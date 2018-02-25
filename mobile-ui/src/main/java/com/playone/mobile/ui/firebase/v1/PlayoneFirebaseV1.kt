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
        playoneDataSnapshot(callback, errorCallback, ::snapToPlayoneList)
    }
    else {
        userDataSnapshot(userId.toString(),
                         {},  // This is redundant anonymous function for running strategy function.
                         errorCallback) { userSnapToPlayoneList(it, errorCallback, callback) }
    }

    override fun getJoinedPlayoneList(
        userId: Int,
        callback: PlayoneCallback<List<PlayoneModel>>,
        errorCallback: FirebaseErrorCallback
    ) = joinedDataSnapshot(userId.toString(), {}, errorCallback) {
        joinedSnapToPlayoneList(it, errorCallback, callback)
    }

    override fun getFavoritePlayoneList(
        userId: Int,
        callback: PlayoneCallback<List<PlayoneModel>>,
        errorCallback: FirebaseErrorCallback
    ) = favoriteDataSnapshot(userId.toString(), {}, errorCallback) {
        favoriteSnapToPlayoneList(it, errorCallback, callback)
    }

    override fun getPlayoneDetail(
        userId: Int,
        callback: (model: PlayoneModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = playoneDataSnapshot(userId.toString(), callback, errorCallback, ::snap2Playone)

    override fun createPlayone(
        userId: Int,
        model: PlayoneModel,
        callback: (isSuccess: Boolean) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = Unit

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

    private fun <D> joinedDataSnapshot(
        userId: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = dbReference
        .child(JOINED)
        .child(userId)
        .addStrategyListener(callback, errorCallback, strategy)

    private fun <D> favoriteDataSnapshot(
        userId: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = dbReference
        .child(USERS)
        .child(userId)
        .child(FAVORITES)
        .addStrategyListener(callback, errorCallback, strategy)

    private fun playoneDataSnapshot(
        test: Int,
        userId: String,
        callback: (isSuccess: Boolean) -> Unit,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<Boolean>
    ) = dbReference
        .child(USERS)
        .child(userId)
        .child(NAME)
        .addListenerForSingleValueEvent {
            onDataChange = { strategy?.apply { callback(this(it)) } }
            onCancelled = { it.makeCallback(errorCallback) }
        }
    //endregion

    //region Strategies of snapshot to the object.
    private fun snapToPlayoneList(dataSnapshot: DataSnapshot?) =
        dataSnapshot.takeIf(DataSnapshot?::isNotNull)
            ?.children
            ?.toMutableList()
            ?.mapNotNull(::toPlayoneModel)
            .orEmpty()

    private fun snapToPlayone(dataSnapshot: DataSnapshot?) =
        dataSnapshot.takeIf(DataSnapshot?::isNotNull)?.run(::toPlayoneModel)

    /**
     * A strategy for transforming from user snapshot to a playone list thru a method
     * [playoneDataSnapshot] to achieve it.
     *
     * @param dataSnapshot a snapshot from the firebase database.
     * @param errorCallback a function for getting a error when retrieving the data.
     * @param block a really block to get a [List].
     */
    private fun userSnapToPlayoneList(
        dataSnapshot: DataSnapshot?,
        errorCallback: FirebaseErrorCallback,
        block: (List<PlayoneModel>) -> Unit
    ) = dataSnapshot
        ?.getValue(UserModel::class.java)
        ?.teams
        ?.keys
        ?.run { byThruId(errorCallback, block) }

    private fun joinedSnapToPlayoneList(
        dataSnapshot: DataSnapshot?,
        errorCallback: FirebaseErrorCallback,
        block: (List<PlayoneModel>) -> Unit
    ) = dataSnapshot
        ?.children
        ?.map { it.key }
        ?.toMutableSet()
        ?.run { byThruId(errorCallback, block) }

    private fun favoriteSnapToPlayoneList(
        dataSnapshot: DataSnapshot?,
        errorCallback: FirebaseErrorCallback,
        block: (List<PlayoneModel>) -> Unit
    ) = dataSnapshot
        ?.children
        ?.filter { it.getValue(Boolean::class.java) ?: false }
        ?.map { it.key }
        ?.toMutableSet()
        ?.run { byThruId(errorCallback, block) }

    private fun snapToIsSuccess() {

    }

    private fun MutableSet<String>.byThruId(
        errorCallback: FirebaseErrorCallback,
        block: (List<PlayoneModel>) -> Unit
    ) {
        val list = mutableListOf<PlayoneModel>()
        forEachIndexed { index, id ->
            playoneDataSnapshot(id,
                                {
                                    it.takeIf(PlayoneModel?::isNotNull)?.let(list::add)
                                    // Finish the loop.
                                    if (index == size - 1) block(list)
                                },
                                errorCallback,
                                ::snapToPlayone)
        }
    }
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