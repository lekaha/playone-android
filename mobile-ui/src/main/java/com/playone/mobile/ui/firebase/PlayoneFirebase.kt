package com.playone.mobile.ui.firebase

import com.google.firebase.database.DatabaseReference
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.ui.firebase.ext.addListenerForSingleValueEvent

abstract class PlayoneFirebase(private val dbReference: DatabaseReference) {
    private val GROUPS = "groups"
    private val USERS = "users"
    private val MEMBERS = "members"
    private val JOINED = "joined"
    private val FAVORITES = "favorites"
    private val TEAMS = "teams"
    private val NAME = "name"

    fun playoneDataSnapshot() {
        dbReference.child(GROUPS).addListenerForSingleValueEvent {
            onDataChange = {
                it.takeIf { it.isNotNull() }?.let {}
            }
            onCancelled = { }
        }
    }
}