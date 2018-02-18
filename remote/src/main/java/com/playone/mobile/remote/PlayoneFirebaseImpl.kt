package com.playone.mobile.remote

import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneFirebase
import com.playone.mobile.ext.reactive.single
import com.playone.mobile.remote.mapper.PlayoneEntityMapper
import io.reactivex.Single

/**
 * Remote implementation for retrieving Playone instances. This class implements the
 * [com.playone.mobile.data.repository.PlayoneFirebase] from the Data layer as it is that
 * layers responsibility for defining the operations in which data store implementation
 * layers can carry out.
 */
class PlayoneFirebaseImpl constructor(
    private val service: PlayoneService,
    private val entityMapper: PlayoneEntityMapper
) : PlayoneFirebase {
    override fun fetchPlayoneList(userId: Int): Single<List<PlayoneEntity>> =
        single<List<PlayoneEntity>> { }
//        .create(getPlayonesDataSnapshot())
//            .flatMap(object : Func1<DataSnapshot, Observable<PlayoneEntity>>() {
//
//                fun call(dataSnapshot: DataSnapshot): Observable<PlayoneEntity> {
//
//                    return Observable.create(object : Observable.OnSubscribe<PlayoneEntity>() {
//                        fun call(subscriber: Subscriber<in PlayoneEntity>) {
//                            val i = dataSnapshot.getChildren().iterator()
//                            while (i.hasNext()) {
//                                val ds = i.next()
//                                if (null != ds) {
//                                    subscriber.onNext(toPlayoneEntity(ds!!))
//                                }
//                            }
//                            subscriber.onCompleted()
//                        }
//                    })
//                }
//            }).toList()

    override fun fetchJoinedPlayoneList(userId: Int) = TODO()

    override fun fetchFavoritePlayoneList(userId: Int) = TODO()

    override fun fetchPlayoneDetail(playoneId: Int) = TODO()

    override fun createPlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun updatePlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean) = TODO()

    override fun sendJoinRequest(playoneId: Int, userId: Int, msg: String) = TODO()

    override fun toggleFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isJoint(playoneId: Int, userId: Int) = TODO()

    override fun userEntity(userId: Int) = TODO()

    override fun createUser(userEntity: UserEntity) = TODO()

    override fun updateUser(userEntity: UserEntity) = TODO()

    override fun updateUser(userEntity: UserEntity, lastDeviceToken: String) = TODO()

    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()

    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()

    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()

    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()
}