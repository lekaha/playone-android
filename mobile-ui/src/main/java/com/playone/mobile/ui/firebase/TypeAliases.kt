package com.playone.mobile.ui.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.storage.UploadTask

typealias PlayoneCallback<D> = (model: D) -> Unit
typealias FirebaseErrorCallback = (code: Int, msg: String, detail: String) -> Unit
typealias OperationResultCallback = (isSuccess: Boolean) -> Unit

typealias DataSnapStrategy<D> = ((DataSnapshot?) -> D)?
typealias TransactionDataSnapStrategy<D> = ((DatabaseError, Boolean, DataSnapshot?) -> D)?

typealias UploadTaskFailureCallback = (e: Exception) -> Unit
typealias UploadTaskSuccessCallback = (snapshot: UploadTask.TaskSnapshot) -> Unit