package com.playone.mobile.ui.firebase.v1

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.MutableData
import com.google.firebase.database.Query
import com.google.firebase.database.Transaction
import com.google.firebase.iid.FirebaseInstanceId
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.remote.OperationResultCallback
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

    override fun createPlayone(
        userId: Int,
        model: PlayoneModel,
        callback: (isSuccess: Boolean) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = playoneDataSnapshotForCreation(userId.toString(), callback, errorCallback) {
        snapToBooleanForPlayoneCreation(it, userId.toString(), model)
    }

    override fun updatePlayone(
        id: Int,
        model: PlayoneModel,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = playoneDataSnapshotForUpdate<Any>(id.toString(),
                                          model,
                                          callback,
                                          errorCallback,
                                          null)

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
    ) = playoneDataSnapshot(userId.toString(), callback, errorCallback, ::snapToPlayone)

    override fun getUser(
        userId: Int,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = userDataSnapshot(userId.toString(), callback, errorCallback, ::snapToUser)

    override fun createUser(
        model: UserModel,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = userDataSnapshot(model.id, {}, errorCallback) {
        snapToBooleanForUserCreation(it, model, callback)
    }

    override fun updateUser(
        model: UserModel,
        lastDeviceToken: String,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) {

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

    private fun playoneDataSnapshotForCreation(
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

    private fun <D> playoneDataSnapshotForUpdate(
        id: String,
        model: PlayoneModel,
        callback: (isSuccess: Boolean) -> Unit,
        errorCallback: FirebaseErrorCallback,
        strategy: TransactionDataSnapStrategy<D>
    ) = dbReference
        .child(GROUPS)
        .child(id)
        .runTransaction(object : Transaction.Handler {
            override fun onComplete(de: DatabaseError, p1: Boolean, ds: DataSnapshot?) =
                if (!p1) {
                    de.makeCallback(errorCallback)
                }
                else {
                    strategy?.invoke(de, p1, ds)
                    callback(p1)
                }

            override fun doTransaction(mutableData: MutableData?): Transaction.Result {
                val pm = mutableData?.getValue(PlayoneModel::class.java)

                return pm?.let {
                    // Assign data from the parameter model to the remote playone model.
                    model.apply {
                        it.name = name
                        it.description = description
                        it.address = address
                        it.date = date
                        it.updated = updated
                        it.latitude = latitude
                        it.longitude = longitude
                        it.limit = limit
                        it.level = level
                    }

                    mutableData.apply { value = it }
                }?.let(Transaction::success) ?: Transaction.success(mutableData)
            }
        })

    private fun <D> userDataSnapshot(
        userId: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = dbReference
        .child(USERS)
        .child(userId)
        .addStrategyListener(callback, errorCallback, strategy)

    private fun <D> userDataSnapshotForUpdate(
        model: UserModel,
        lastDeviceToken: String? = null,
        callback: (isSuccess: Boolean) -> Unit,
        errorCallback: FirebaseErrorCallback,
        strategy: TransactionDataSnapStrategy<D>
    ) = dbReference
        .child(USERS)
        .child(model.id)
        .runTransaction(object : Transaction.Handler {
            override fun onComplete(de: DatabaseError, p1: Boolean, ds: DataSnapshot?) = Unit

            override fun doTransaction(mutableData: MutableData?): Transaction.Result {
                val um = mutableData?.getValue(UserModel::class.java)

                return um?.let {
                    model.apply {
                        it.name = name
                        it.description = description
                        it.years = years
                        it.grade = grade
                        it.age = age
                        it.level = level
                    }

                    mutableData.apply {
                        value = it
                        // Remove out of date device token.
                        if (lastDeviceToken.isNotNull()) {
                            child(DEVICE_TOKENS).child(lastDeviceToken).value = null
                            model.deviceToken
                                .takeIf { it.isNotBlank() }
                                ?.let { child(DEVICE_TOKENS).child(it).value = true }
                        }
                        else {
                            child(DEVICE_TOKENS).child(model.deviceToken).value = true
                        }
                    }
                }?.let(Transaction::success) ?: Transaction.success(mutableData)
            }
        })
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

    private fun snapToBooleanForPlayoneCreation(
        dataSnapshot: DataSnapshot?,
        userId: String,
        model: PlayoneModel
    ) = dbReference.run {
        val id = child(GROUPS).push().key
        val name = dataSnapshot?.getValue(String::class.java).orEmpty()

        child(USERS).child(userId).child(TEAMS).child(id).setValue(true)

        val copyModel = model.copy(id = id, host = name, userId = userId)

        updateChildren(hashMapOf("/$GROUPS/" to copyModel.toMap()) as Map<String, Any>)

        true
    }

    private fun snapToUser(dataSnapshot: DataSnapshot?) =
        dataSnapshot?.getValue(UserModel::class.java)

    private fun snapToBooleanForUserCreation(
        dataSnapshot: DataSnapshot?,
        model: UserModel,
        block: (UserModel) -> Unit
    ) = dataSnapshot?.takeIf { it.exists() }?.run {
        if (child(DEVICE_TOKENS).exists()) {
            model.deviceToken = FirebaseInstanceId.getInstance().token.orEmpty()
            // FIXME(jieyi): 2018/02/26 Something here!!  `updateUserEntity(userEntity).subscribe(emitter);`
        }
        block(model)
    } ?: let {
        dbReference.child(USERS).child(model.id).apply {
            setValue(model)
            // Add device token.
            child(DEVICE_TOKENS).child(model.deviceToken).setValue(true)
        }
        block(model)
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