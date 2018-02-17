package com.playone.mobile.remote.model

data class NotificationPayloadModel(
    var type: String = "",
    var senderToken: String = "",
    var receiverToken: String = "",
    var teamId: String = "",
    var groupId: String = "",
    var msg: String = ""
)