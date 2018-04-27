package com.playone.mobile.ui.firebase.v1

import com.playone.mobile.remote.bridge.playone.FirebaseErrorCallback
import com.playone.mobile.remote.bridge.playone.OperationResultCallback
import com.playone.mobile.remote.bridge.playone.PlayoneFirebase
import com.playone.mobile.remote.bridge.playone.PlayoneListCallback
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

class StandaloneFirebaseV1 : PlayoneFirebase() {

    override fun obtainPlayoneList(
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun obtainPlayoneList(
        userId: String,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun createPlayone(
        userId: String,
        model: PlayoneModel,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun updatePlayone(
        id: String,
        model: PlayoneModel,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun obtainJoinedPlayoneList(
        userId: Int,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun obtainFavoritePlayoneList(
        userId: Int,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun obtainPlayoneDetail(
        userId: Int,
        callback: (model: PlayoneModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun obtainUser(
        userId: Int,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun obtainUser(
        email: String,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun createUser(
        model: UserModel,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun updateUser(
        model: UserModel,
        lastDeviceToken: String?,
        callback: (mode: UserModel?) -> Unit,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun joinTeamAsMember(
        playoneId: Int,
        userId: Int,
        isJoin: Boolean,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun toggleFavorite(
        playoneId: Int,
        userId: Int,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun isFavorite(
        playoneId: Int,
        userId: Int,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()

    override fun isJoined(
        playoneId: Int,
        userId: Int,
        callback: OperationResultCallback,
        errorCallback: FirebaseErrorCallback
    ) = TODO()
}