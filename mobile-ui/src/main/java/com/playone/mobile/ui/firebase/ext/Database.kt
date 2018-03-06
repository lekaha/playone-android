package com.playone.mobile.ui.firebase.ext

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

/**
 * An extension collections of the firebase database.
 */
class WrapperValueEventListener {

    var onCancelled: (error: DatabaseError) -> Unit = {}
    var onDataChange: (dataSnapshot: DataSnapshot?) -> Unit = {}
}

class WrapperChildEventListener {

    var onCancelled: (error: DatabaseError) -> Unit = {}
    var onChildMoved: (dataSnapshot: DataSnapshot?, p1: String) -> Unit = { _, _ -> }
    var onChildChanged: (dataSnapshot: DataSnapshot?, p1: String) -> Unit = { _, _ -> }
    var onChildAdded: (dataSnapshot: DataSnapshot?, p1: String) -> Unit = { _, _ -> }
    var onChildRemoved: (dataSnapshot: DataSnapshot?) -> Unit = {}
}

fun Query.addListenerForSingleValueEvent(wrapper: WrapperValueEventListener.() -> Unit) =
    WrapperValueEventListener().apply(wrapper).let {
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) = it.onCancelled.invoke(error)

            override fun onDataChange(dataSnapshot: DataSnapshot?) =
                it.onDataChange.invoke(dataSnapshot)
        })
    }

fun Query.addValueEventListener(wrapper: WrapperValueEventListener.() -> Unit) =
    WrapperValueEventListener().apply(wrapper).let {
        addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) = it.onCancelled.invoke(error)

            override fun onDataChange(dataSnapshot: DataSnapshot?) =
                it.onDataChange.invoke(dataSnapshot)
        })
    }

fun Query.addChildEventListener(wrapper: WrapperChildEventListener.() -> Unit) =
    WrapperChildEventListener().apply(wrapper).let {
        addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) = it.onCancelled.invoke(error)

            override fun onChildMoved(dataSnapshot: DataSnapshot?, p1: String) =
                it.onChildMoved.invoke(dataSnapshot, p1)

            override fun onChildChanged(dataSnapshot: DataSnapshot?, p1: String) =
                it.onChildChanged.invoke(dataSnapshot, p1)

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String) =
                it.onChildAdded.invoke(dataSnapshot, p1)

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) =
                it.onChildRemoved.invoke(dataSnapshot)
        })
    }
