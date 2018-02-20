package com.playone.mobile.ui.firebase.v1

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.playone.mobile.ext.isNotNull
import com.playone.mobile.remote.PlayoneFirebase
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import com.playone.mobile.ui.firebase.ext.addListenerForSingleValueEvent

class PlayoneFirebaseV1(
    private val dbReference: DatabaseReference
) : PlayoneFirebase() {
    override fun getPlayoneList(
        userId: Int,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    ) = if (0 < userId) {
        playoneDataSnapshot(callback, errorCallback)
    }
    else {
        playoneDataSnapshot(userId.toString(), callback, errorCallback)
    }

    private fun playoneDataSnapshot(
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    ) {
        dbReference.child(GROUPS).addListenerForSingleValueEvent {
            onDataChange = {
                val listPlayone = it.takeIf { it.isNotNull() }
                    ?.children
                    ?.toMutableList()
                    ?.mapNotNull(::toPlayoneModel)
                    .orEmpty()
                callback(listPlayone)
            }
            onCancelled = { it.makeCallback(errorCallback) }
        }
    }

    private fun playoneDataSnapshot(
        userId: String,
        callback: PlayoneListCallback,
        errorCallback: FirebaseErrorCallback
    ) {
        dbReference.child(USERS).child(userId).addListenerForSingleValueEvent {
            onDataChange = {
                val ue = it?.getValue(UserModel::class.java)
                val size = ue?.teams?.keys?.size ?: 0
                val playoneList = mutableListOf<PlayoneModel>()
                ue?.teams?.keys?.forEach {
                    getPlayoneById(it, {
                        playoneList.add(it)
                        // Wait for all items collected.
                        if (size == playoneList.size) callback(playoneList)
                    }, errorCallback)
                }
            }
            onCancelled = { it.makeCallback(errorCallback) }
        }
    }

    private fun getPlayoneById(
        id: String,
        callback: PlayoneCallback,
        errorCallback: FirebaseErrorCallback
    ) {
        dbReference.child(GROUPS).child(id).addListenerForSingleValueEvent {
            onDataChange = {
                it.takeIf(DataSnapshot?::isNotNull)?.run(::toPlayoneModel)?.let(callback)
            }
            onCancelled = { it.makeCallback(errorCallback) }
        }
    }

    //region Extension function
    private fun toPlayoneModel(dataSnapshot: DataSnapshot) = dataSnapshot.run {
        getValue(PlayoneModel::class.java)
            .takeIf { it.isNotNull() && key.isNotNull() }
            ?.also { it.id = key }
    }

    private fun DatabaseError.makeCallback(errorCallback: FirebaseErrorCallback) =
        errorCallback(code, message, details)
    //endregion
}