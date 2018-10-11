package com.playone.mobile.ui.firebase.v1

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.MutableData
import com.google.firebase.database.Query
import com.google.firebase.database.Transaction
import com.playone.mobile.remote.bridge.playone.OperationResultCallback
import com.playone.mobile.ui.firebase.DataSnapStrategy
import com.playone.mobile.ui.firebase.FirebaseErrorCallback
import com.playone.mobile.ui.firebase.PlayoneCallback
import com.playone.mobile.ui.firebase.TransactionDataSnapStrategy
import com.playone.mobile.ui.firebase.ext.addListenerForSingleValueEvent

internal fun DatabaseError.makeCallback(errorCallback: FirebaseErrorCallback) =
    errorCallback(code, message, details)

internal fun <D> Query.addStrategyListener(
    callback: PlayoneCallback<D>,
    errorCallback: FirebaseErrorCallback,
    strategy: DataSnapStrategy<D>
) = addListenerForSingleValueEvent {
    onDataChange = { strategy?.apply { callback(this(it)) } }
    onCancelled = { it.makeCallback(errorCallback) }
}

internal fun <D> DatabaseReference.runTransaction(
    callback: OperationResultCallback,
    errorCallback: FirebaseErrorCallback,
    strategy: TransactionDataSnapStrategy<D>,
    block: (MutableData?) -> Transaction.Result
) = runTransaction(object : Transaction.Handler {

    override fun onComplete(de: DatabaseError?, p1: Boolean, ds: DataSnapshot?) {
        de?.also {
            if (!p1) {
                it.makeCallback(errorCallback)
            }
            else {
                strategy?.invoke(it, p1, ds)
                callback(p1)
            }
        }
    }

    override fun doTransaction(mutableData: MutableData) = block(mutableData)
})