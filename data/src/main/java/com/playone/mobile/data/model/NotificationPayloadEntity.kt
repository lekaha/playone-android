package com.playone.mobile.data.model

data class NotificationPayloadEntity(
    var type: String = "",
    var senderToken: String = "",
    var receiverToken: String = "",
    var teamId: String = "",
    var groupId: String = "",
    var msg: String = ""
)