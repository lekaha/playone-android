package com.playone.mobile.ui.firebase.v1

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.remote.PlayoneFirebase
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import com.playone.mobile.ui.firebase.ext.addListenerForSingleValueEvent

class PlayoneFirebaseV1(
    private val dbReference: DatabaseReference
) : PlayoneFirebase() {

    /**
     * Retrieve the [List] of the [PlayoneModel] from the firebase server. We'll use
     * the callback function for returning the value back to.
     *
     * @param userId user id.
     * @param callback a function for fetching a [List] of the [PlayoneModel].
     * @param errorCallback a function for getting a error when retrieving the data.
     */
    override fun getPlayoneList(
        userId: Int,
        callback: PlayoneCallback<List<PlayoneModel>>,
        errorCallback: FirebaseErrorCallback
    ) = if (0 < userId) {
        playoneDataSnapshot(callback, errorCallback, ::playoneSnap2PlayoneList)
    }
    else {
        userDataSnapshot(userId.toString(),
                         {},  // This is redundant anonymous function for running strategy function.
                         errorCallback,
                         {
                             userSnap2PlayoneList(it, errorCallback, callback)
                             emptyList<List<PlayoneModel>>()
                         })
    }

    //region Fetching data from firebase database.
    private fun <D> playoneDataSnapshot(
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = dbReference
        .child(GROUPS)
        .addStrategyListener(callback, errorCallback, strategy)

    private fun <D> playoneDataSnapshot(
        id: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = dbReference
        .child(GROUPS)
        .child(id)
        .addStrategyListener(callback, errorCallback, strategy)

    private fun <D> userDataSnapshot(
        userId: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = dbReference
        .child(USERS)
        .child(userId)
        .addStrategyListener(callback, errorCallback, strategy)
    //endregion

    //region Strategies of snapshot 2 the object.
    private fun playoneSnap2PlayoneList(dataSnapshot: DataSnapshot?) =
        dataSnapshot.takeIf(DataSnapshot?::isNotNull)
            ?.children
            ?.toMutableList()
            ?.mapNotNull(::toPlayoneModel)
            .orEmpty()

    /**
     * A strategy for transforming from user snapshot to a playone list thru a method
     * [playoneDataSnapshot] to achieve it.
     *
     * @param dataSnapshot a snapshot from the firebase database.
     * @param errorCallback a function for getting a error when retrieving the data.
     * @param block a really block to get a [List].
     */
    private fun userSnap2PlayoneList(
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
                                ::playoneSnap2Playone)
        }
    }

    private fun playoneSnap2Playone(dataSnapshot: DataSnapshot?) =
        dataSnapshot.takeIf(DataSnapshot?::isNotNull)?.run(::toPlayoneModel)
    //endregion

    //region Extension function
    private fun toPlayoneModel(dataSnapshot: DataSnapshot) = dataSnapshot.run {
        getValue(PlayoneModel::class.java)
            .takeIf { it.isNotNull() && key.isNotNull() }
            ?.also { it.id = key }
    }

    private fun DatabaseError.makeCallback(errorCallback: FirebaseErrorCallback) =
        errorCallback(code, message, details)

    private fun <D> Query.addStrategyListener(
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = addListenerForSingleValueEvent {
        onDataChange = { strategy?.apply { callback(this(it)) } }
        onCancelled = { it.makeCallback(errorCallback) }
    }
    //endregion
}