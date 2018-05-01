package com.playone.mobile.remote.bridge.playone

import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

abstract class PlayoneFirebase {
    protected val GROUPS = "groups"
    protected val USERS = "users"
    protected val MEMBERS = "members"
    protected val JOINED = "joined"
    protected val FAVORITES = "favorites"
    protected val TEAMS = "teams"
    protected val NAME = "name"
    protected val DEVICE_TOKENS = "device_tokens"

    abstract fun obtainPlayoneList(
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun obtainPlayoneList(
        userId: String,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun createPlayone(
        userId: String,
        model: PlayoneModel,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun updatePlayone(
        id: String,
        model: PlayoneModel,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun obtainJoinedPlayoneList(
        userId: String,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun obtainFavoritePlayoneList(
        userId: String,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun obtainPlayoneDetail(
        userId: String,
        callback: (model: PlayoneModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun obtainUser(
        userId: String,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun obtainUserByEmail(
        email: String,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun createUser(
        model: UserModel,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun updateUser(
        model: UserModel,
        lastDeviceToken: String?,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun joinTeamAsMember(
        playoneId: String,
        userId: String,
        isJoin: Boolean,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun toggleFavorite(
        playoneId: String,
        userId: String,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun isFavorite(
        playoneId: String,
        userId: String,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun isJoined(
        playoneId: String,
        userId: String,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    )
}