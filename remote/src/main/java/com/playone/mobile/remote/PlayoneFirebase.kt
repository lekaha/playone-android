package com.playone.mobile.remote

import com.playone.mobile.remote.model.PlayoneModel

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
        callback: (model: List<PlayoneModel>) -> Unit,
        errorCallback: (code: Int, msg: String, detail: String) -> Unit
    )

    abstract fun getJoinedPlayoneList(
        userId: Int,
        callback: (model: List<PlayoneModel>) -> Unit,
        errorCallback: (code: Int, msg: String, detail: String) -> Unit
    )

    abstract fun getFavoritePlayoneList(
        userId: Int,
        callback: (model: List<PlayoneModel>) -> Unit,
        errorCallback: (code: Int, msg: String, detail: String) -> Unit
    )

    abstract fun getPlayoneDetail(
        userId: Int,
        callback: (model: PlayoneModel?) -> Unit,
        errorCallback: (code: Int, msg: String, detail: String) -> Unit
    )

    abstract fun createPlayone(
        userId: Int,
        model: PlayoneModel,
        callback: (isSuccess: Boolean) -> Unit,
        errorCallback: (code: Int, msg: String, detail: String) -> Unit
    )
}