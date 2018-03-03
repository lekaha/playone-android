package com.playone.mobile.ui.firebase.v1

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Transaction
import com.google.firebase.iid.FirebaseInstanceId
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.remote.bridge.playone.OperationResultCallback
import com.playone.mobile.remote.bridge.playone.PlayoneFirebase
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
    override fun obtainPlayoneList(
        userId: Int,
        callback: PlayoneCallback<List<PlayoneModel>>,
        errorCallback: FirebaseErrorCallback
    ) = if (0 < userId) {
        playoneDsAction(callback, errorCallback, ::snapToPlayoneList)
    }
    else {
        userDsAction(userId.toString(),
                     {},  // This is redundant anonymous function for running strategy function.
                     errorCallback) { userSnapToPlayoneList(it, errorCallback, callback) }
    }

    override fun createPlayone(
        userId: Int,
        model: PlayoneModel,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = playoneDsForCreation(userId.toString(), callback, errorCallback) {
        snapToBooleanForPlayoneCreation(it, userId.toString(), model)
    }

    override fun updatePlayone(
        id: Int,
        model: PlayoneModel,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = playoneDsForUpdate<Any>(id.toString(),
                                model,
                                callback,
                                errorCallback,
                                null)

    override fun obtainJoinedPlayoneList(
        userId: Int,
        callback: PlayoneCallback<List<PlayoneModel>>,
        errorCallback: FirebaseErrorCallback
    ) = joinedDsAction(userId.toString(), {}, errorCallback) {
        joinedSnapToPlayoneList(it, errorCallback, callback)
    }

    override fun obtainFavoritePlayoneList(
        userId: Int,
        callback: PlayoneCallback<List<PlayoneModel>>,
        errorCallback: FirebaseErrorCallback
    ) = favoriteDsAction(userId.toString(), {}, errorCallback) {
        favoriteSnapToPlayoneList(it, errorCallback, callback)
    }

    override fun obtainPlayoneDetail(
        userId: Int,
        callback: (model: PlayoneModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = playoneDsAction(userId.toString(), callback, errorCallback, ::snapToPlayone)

    override fun obtainUser(
        userId: Int,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = userDsAction(userId.toString(), callback, errorCallback, ::snapToUser)

    override fun createUser(
        model: UserModel,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = userDsAction(model.id, {}, errorCallback) {
        snapToBooleanForUserCreation(it, model, { user ->
            updateUser(user, null, { callback(user) }, errorCallback)
        })
    }

    override fun updateUser(
        model: UserModel,
        lastDeviceToken: String?,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = userDsForUpdate<Boolean>(model, lastDeviceToken, callback, errorCallback, null)

    override fun joinTeamAsMember(
        playoneId: Int,
        userId: Int,
        isJoin: Boolean,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) {
        val (pId, uId) = playoneId.toString() to userId.toString()

        memberSnapshot.child(pId).child(uId).setValue(isJoin)
        joinSnapshot.child(uId).child(pId).setValue(isJoin)
        playoneOfUserDsAction<Boolean>(pId, uId, isJoin, callback, errorCallback, null)
    }

    override fun toggleFavorite(
        playoneId: Int,
        userId: Int,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = favoriteDsAction(userId.toString(), {}, errorCallback) {
        var isFavorite = false
        val (pId, uId) = playoneId.toString() to userId.toString()

        when (it?.exists()) {
            true -> {
                // OPTIMIZE(jieyi): 2018/02/27 `getValue` may be null point, this case it will
                // be false temporally.
                isFavorite = it.getValue(Boolean::class.java) ?: false
                favoriteSnapshot(uId).child(pId).setValue(!isFavorite)
            }
            false -> favoriteSnapshot(uId).child(pId).setValue(true)
            null -> {
                // No-op.
            }
        }

        callback(isFavorite)
    }

    override fun isFavorite(
        playoneId: Int,
        userId: Int,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = favoriteDsAction(userId.toString(), {}, errorCallback) {
        snapToRequestFavorite(it, playoneId.toString(), callback)
    }

    override fun isJoined(
        playoneId: Int,
        userId: Int,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = joinedDsAction(userId.toString(), {}, errorCallback, {
        snapToRequestJoined(it, playoneId.toString(), callback)
    })

    //region Fetching data from firebase database.
    private fun <D> playoneDsAction(
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = playoneSnapshot.addStrategyListener(callback, errorCallback, strategy)

    private fun <D> playoneDsAction(
        id: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = playoneSnapshot.child(id).addStrategyListener(callback, errorCallback, strategy)

    private fun <D> joinedDsAction(
        userId: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = joinSnapshot.child(userId).addStrategyListener(callback, errorCallback, strategy)

    private fun <D> favoriteDsAction(
        userId: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = favoriteSnapshot(userId).addStrategyListener(callback, errorCallback, strategy)

    private fun playoneDsForCreation(
        userId: String,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<Boolean>
    ) = userSnapshot
        .child(userId)
        .child(NAME)
        .addListenerForSingleValueEvent {
            onDataChange = { strategy?.apply { callback(this(it)) } }
            onCancelled = { it.makeCallback(errorCallback) }
        }

    private fun <D> playoneDsForUpdate(
        id: String,
        model: PlayoneModel,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback,
        strategy: TransactionDataSnapStrategy<D>
    ) = playoneSnapshot.child(id).runTransaction(callback, errorCallback, strategy) { mutableData ->
        val pm = mutableData?.getValue(PlayoneModel::class.java)

        pm?.let {
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

    private fun <D> userDsAction(
        userId: String,
        callback: PlayoneCallback<D>,
        errorCallback: FirebaseErrorCallback,
        strategy: DataSnapStrategy<D>
    ) = userSnapshot.child(userId).addStrategyListener(callback, errorCallback, strategy)

    private fun <D> userDsForUpdate(
        model: UserModel,
        lastDeviceToken: String? = null,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback,
        strategy: TransactionDataSnapStrategy<D>
    ) = userSnapshot
        .child(model.id)
        .runTransaction(object : Transaction.Handler {
            override fun onComplete(de: DatabaseError, p1: Boolean, ds: DataSnapshot?) {
                if (!p1) {
                    de.makeCallback(errorCallback)
                }
                else {
                    strategy?.invoke(de, p1, ds)
                    callback(model)
                }
            }

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

    private fun <D> playoneOfUserDsAction(
        playoneId: String,
        userId: String,
        isJoin: Boolean,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback,
        strategy: TransactionDataSnapStrategy<D>
    ) = joinSnapshot
        .child(userId)
        .child(playoneId)
        .runTransaction(callback, errorCallback, strategy) { mutableData ->
            val join = mutableData?.getValue(Boolean::class.java)
            // [mutableData] must not be null here.
            if (join.isNotNull()) mutableData?.value = isJoin
            Transaction.success(mutableData)
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
     * [playoneDsAction] to achieve it.
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
    ) = let {
        val id = playoneSnapshot.push().key
        val name = dataSnapshot?.getValue(String::class.java).orEmpty()

        teamSnapshot(userId).child(id).setValue(true)

        val copyModel = model.copy(id = id, host = name, userId = userId)

        dbReference.updateChildren(hashMapOf("/$GROUPS/" to copyModel.toMap()) as Map<String, Any>)

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
        }
        block(model)
    } ?: let {
        userSnapshot.child(model.id).apply {
            setValue(model)
            // Add device token.
            child(DEVICE_TOKENS).child(model.deviceToken).setValue(true)
        }
        block(model)
    }

    private fun snapToRequestFavorite(
        dataSnapshot: DataSnapshot?,
        playoneId: String,
        block: (Boolean) -> Unit
    ) = dataSnapshot?.child(playoneId).also {
        val isFavorite =
            (if (true == it?.exists()) it.getValue(Boolean::class.java) else false) ?: false
        block(isFavorite)
    }

    private fun snapToRequestJoined(
        dataSnapshot: DataSnapshot?,
        playoneId: String,
        block: (Boolean) -> Unit
    ) = dataSnapshot?.child(playoneId).also {
        val isJoined =
            (if (true == it?.exists()) it.getValue(Boolean::class.java) else false) ?: false
        block(isJoined)
    }

    private fun MutableSet<String>.byThruId(
        errorCallback: FirebaseErrorCallback,
        block: (List<PlayoneModel>) -> Unit
    ) {
        val list = mutableListOf<PlayoneModel>()
        forEachIndexed { index, id ->
            playoneDsAction(id,
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

    private fun toPlayoneModel(dataSnapshot: DataSnapshot) = dataSnapshot.run {
        getValue(PlayoneModel::class.java)
            .takeIf { it.isNotNull() && key.isNotNull() }
            ?.also { it.id = key }
    }

    private val playoneSnapshot get() = dbReference.child(GROUPS)

    private val userSnapshot get() = dbReference.child(USERS)

    private val joinSnapshot get() = dbReference.child(JOINED)

    private val memberSnapshot get() = dbReference.child(MEMBERS)

    private fun favoriteSnapshot(userId: String) = playoneSnapshot.child(userId).child(FAVORITES)

    private fun teamSnapshot(userId: String) = playoneSnapshot.child(userId).child(TEAMS)
}