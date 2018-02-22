package com.playone.mobile.remote

abstract class PlayoneFirebase {
    protected val GROUPS = "groups"
    protected val USERS = "users"
    protected val MEMBERS = "members"
    protected val JOINED = "joined"
    protected val FAVORITES = "favorites"
    protected val TEAMS = "teams"
    protected val NAME = "name"

    abstract fun getPlayoneList(
        userId: Int,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun getJoinedPlayoneList(
        userId: Int,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    )

    abstract fun getFavoritePlayoneList(
        userId: Int,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    )
}