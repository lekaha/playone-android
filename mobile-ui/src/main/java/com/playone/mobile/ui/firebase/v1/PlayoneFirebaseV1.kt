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
        playoneDataSnapshot(callback, errorCallback, ::snap2Playone)
    }
    else {
        userDataSnapshot(userId.toString(), callback, errorCallback, )
    }

    //region Fetching data from firebase database.
    private fun <D> playoneDataSnapshot(
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) {
        dbReference.child(GROUPS).addListenerForSingleValueEvent {
            onDataChange = { callback(strategy(it)) }
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
            onDataChange = { callback(strategy(it)) }
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
            onDataChange = {
                callback(strategy(it))

                val ue = it?.getValue(UserModel::class.java)
                val size = ue?.teams?.keys?.size ?: 0
                val playoneList = mutableListOf<PlayoneModel>()

                ue?.teams?.keys?.forEach {
                    playoneDataSnapshot<PlayoneModel>(it, {
                    }, errorCallback, {})
                    //                    playoneDataSnapshot<PlayoneModel>(it, {
//                        playoneList.add(it)
//                        // Wait for all items collected.
//                        if (size == playoneList.size) callback(playoneList)
//                    }, errorCallback)
                }
            }
            onCancelled = { it.makeCallback(errorCallback) }
        }
    }

    //endregion

    private fun snap2Playone(dataSnapshot: DataSnapshot?) =
        dataSnapshot.takeIf { it.isNotNull() }
            ?.children
            ?.toMutableList()
            ?.mapNotNull(::toPlayoneModel)
            .orEmpty()

    private fun snap2PlayoneList(dataSnapshot: DataSnapshot?) = dataSnapshot.let {
        val ue = it?.getValue(UserModel::class.java)
        val size = ue?.teams?.keys?.size ?: 0
        val playoneList = mutableListOf<PlayoneModel>()

        ue?.teams?.keys?.forEach {
            playoneDataSnapshot<PlayoneModel>(it, {
                //                playoneList.add(it)
                // Wait for all items collected.
//                if (size == playoneList.size) callback(playoneList)
            }, null) {
                snap2Playone(it) {}
            }
        }
    }

    private fun snap2Playone(dataSnapshot: DataSnapshot?, callback: (PlayoneModel) -> Unit) =
        dataSnapshot.takeIf(DataSnapshot?::isNotNull)?.run(::toPlayoneModel)?.let(callback)

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