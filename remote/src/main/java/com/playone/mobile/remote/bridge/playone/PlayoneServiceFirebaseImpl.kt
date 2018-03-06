package com.playone.mobile.remote.bridge.playone

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.repository.PlayoneRemote
import com.playone.mobile.data.repository.PlayoneRemote.Result.ERROR
import com.playone.mobile.data.repository.PlayoneRemote.Result.SUCCESS
import com.playone.mobile.ext.reactive.single
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel

/**
 * An implementation of [com.playone.mobile.remote.PlayoneService] that retrieving
 * Playone data from Firebase and using the Firebase SDK in this class.
 */
class PlayoneServiceFirebaseImpl(
    private val playoneFirebase: PlayoneFirebase
) : PlayoneService {

    override fun retrievePlayoneList(userId: Int) =
        single<List<PlayoneModel>> { emitter ->
            playoneFirebase.obtainPlayoneList(userId, emitter::onSuccess, emitter::errorHandler)
        }

    override fun retrieveJoinedPlayoneList(userId: Int) =
        single<List<PlayoneModel>> { emitter ->
            playoneFirebase.obtainJoinedPlayoneList(userId,
                                                    emitter::onSuccess,
                                                    emitter::errorHandler)
        }

    override fun retrieveFavoritePlayoneList(userId: Int) =
        single<List<PlayoneModel>> { emitter ->
            playoneFirebase.obtainFavoritePlayoneList(userId,
                                                      emitter::onSuccess,
                                                      emitter::errorHandler)
        }

    override fun retrievePlayoneDetail(playoneId: Int) =
        single<PlayoneModel> { emitter ->
            playoneFirebase.obtainPlayoneDetail(playoneId,
                                                { it?.let(emitter::onSuccess) ?: emitter.errorNullObject() },
                                                emitter::errorHandler)
        }

    override fun createPlayoneDetail(userId: Int, playoneModel: PlayoneModel) =
        single<PlayoneRemote.Result> { emitter ->
            playoneFirebase.createPlayone(userId, playoneModel, {
                emitter.onSuccess(if (it) SUCCESS else ERROR)
            }, emitter::errorHandler)
        }

    override fun updatePlayoneDetail(userId: Int, playoneModel: PlayoneModel) =
        single<PlayoneRemote.Result> { emitter ->
            playoneFirebase.updatePlayone(userId, playoneModel, {
                emitter.onSuccess(if (it) SUCCESS else ERROR)
            }, emitter::errorHandler)
        }

    override fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean) =
        single<PlayoneRemote.Result> { emitter ->
            playoneFirebase.joinTeamAsMember(playoneId, userId, isJoin, {
                emitter.onSuccess(if (it) SUCCESS else ERROR)
            }, emitter::errorHandler)
        }

    override fun sendJoinRequest(playoneId: Int, userId: Int, msg: String) = TODO()

    override fun toggleFavorite(playoneId: Int, userId: Int) =
        single<Boolean> { emitter ->
            playoneFirebase.toggleFavorite(playoneId,
                                           userId,
                                           emitter::onSuccess,
                                           emitter::errorHandler)
        }

    override fun isFavorite(playoneId: Int, userId: Int) =
        single<Boolean> { emitter ->
            playoneFirebase.isFavorite(playoneId, userId, emitter::onSuccess, emitter::errorHandler)
        }

    override fun isJoined(playoneId: Int, userId: Int) =
        single<Boolean> { emitter ->
            playoneFirebase.isJoined(playoneId, userId, emitter::onSuccess, emitter::errorHandler)
        }

    //region For Auth0
    override fun retrieveUserModel(userId: Int) =
        single<UserModel> { emitter ->
            playoneFirebase.obtainUser(userId,
                                       { it?.let(emitter::onSuccess) ?: emitter.errorNullObject() },
                                       emitter::errorHandler)
        }

    override fun retrieveUserModel(email: String) =
        single<UserModel> { emitter ->
            playoneFirebase.obtainUser(email,
                                       { it?.let(emitter::onSuccess) ?: emitter.errorNullObject() },
                                       emitter::errorHandler)
        }

    override fun createUser(userModel: UserModel) = single<UserModel> { emitter ->
        playoneFirebase.createUser(userModel,
                                   { it?.let(emitter::onSuccess) ?: emitter.errorNullObject() },
                                   emitter::errorHandler)
    }

    override fun updateUser(userModel: UserModel) = single<UserModel> { emitter ->
        playoneFirebase.updateUser(userModel, null,
                                   { it?.let(emitter::onSuccess) ?: emitter.errorNullObject() },
                                   emitter::errorHandler)
    }

    override fun updateUser(userModel: UserModel, lastDeviceToken: String) =
        single<UserModel> { emitter ->
            playoneFirebase.updateUser(userModel,
                                       lastDeviceToken,
                                       { it?.let(emitter::onSuccess) ?: emitter.errorNullObject() },
                                       emitter::errorHandler)
        }
    //endregion

    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()

    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()

    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()

    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()

}