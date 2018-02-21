package com.playone.mobile.ui.firebase.v1

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.remote.PlayoneFirebase
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import com.playone.mobile.ui.firebase.ext.addListenerForSingleValueEvent

class PlayoneFirebaseV1(
    private val dbReference: DatabaseReference
) : PlayoneFirebase() {

    override fun getPlayoneList(
        userId: Int,
        callback: PlayoneCallback<List<PlayoneModel>>,
        errorCallback: FirebaseErrorCallback
    ) = if (0 < userId) {
        playoneDataSnapshot(callback, errorCallback, ::snap2PlayoneList)
    }
    else {
//        userDataSnapshot<List<PlayoneModel>>(userId.toString(),
//                                             callback,
//                                             errorCallback,
//                                             { snap2PlayoneList(it, errorCallback, callback) })
    }

    //region Fetching data from firebase database.
    private fun <D> playoneDataSnapshot(
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) {
        dbReference.child(GROUPS).addListenerForSingleValueEvent {
            onDataChange = { strategy?.apply { callback(this(it)) } }
            onCancelled = { it.makeCallback(errorCallback) }
        }
    }

    private fun <D> playoneDataSnapshot(
        id: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) {
        dbReference.child(GROUPS).child(id).addListenerForSingleValueEvent {
            onDataChange = { strategy?.apply { callback(this(it)) } }
            onCancelled = { it.makeCallback(errorCallback) }
        }
    }

    private fun <D> userDataSnapshot(
        userId: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) {
        dbReference.child(USERS).child(userId).addListenerForSingleValueEvent {
            onDataChange = { strategy?.apply { callback(this(it)) } }
            onCancelled = { it.makeCallback(errorCallback) }
        }
    }

    //endregion

    private fun snap2PlayoneList(dataSnapshot: DataSnapshot?) =
        dataSnapshot.takeIf(DataSnapshot?::isNotNull)
            ?.children
            ?.toMutableList()
            ?.mapNotNull(::toPlayoneModel)
            .orEmpty()

    // OPTIMIZE(jieyi): 2018/02/21 Modified to async & await.
    private fun snap2PlayoneList(
        dataSnapshot: DataSnapshot?,
        errorCallback: FirebaseErrorCallback,
        block: (List<PlayoneModel>) -> Unit
    ) = dataSnapshot?.getValue(UserModel::class.java)?.teams?.keys?.run {
        val list = mutableListOf<PlayoneModel>()
        forEachIndexed { index, id ->
            playoneDataSnapshot(id,
                                {
                                    it.takeIf(PlayoneModel?::isNotNull)?.let(list::add)
                                    // Finish the loop.
                                    if (index == size - 1) block(list)
                                },
                                errorCallback,
                                ::snap2Playone)
        }
    }

    private fun snap2Playone(dataSnapshot: DataSnapshot?) =
        dataSnapshot.takeIf(DataSnapshot?::isNotNull)?.run(::toPlayoneModel)

    //region Extension function
    private fun toPlayoneModel(dataSnapshot: DataSnapshot) = dataSnapshot.run {
        getValue(PlayoneModel::class.java)
            .takeIf { it.isNotNull() && key.isNotNull() }
            ?.also { it.id = key }
    }

    private fun DatabaseError.makeCallback(errorCallback: FirebaseErrorCallback) =
        errorCallback(code, message, details)
    //endregion
}