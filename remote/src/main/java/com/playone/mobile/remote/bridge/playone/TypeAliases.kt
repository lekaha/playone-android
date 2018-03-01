package com.playone.mobile.remote.bridge.playone

import com.playone.mobile.remote.model.PlayoneModel

typealias PlayoneListCallback = (model: List<PlayoneModel>) -> Unit
typealias FirebaseErrorCallback = (code: Int, msg: String, detail: String) -> Unit
typealias OperationResultCallback = (isSuccess: Boolean) -> Unit