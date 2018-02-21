package com.playone.mobile.ui.firebase.v1

import com.google.firebase.database.DataSnapshot

typealias PlayoneCallback<D> = (model: D) -> Unit
typealias FirebaseErrorCallback = (code: Int, msg: String, detail: String) -> Unit

typealias DataSnapStrategy<D> = ((DataSnapshot?) -> D)?