package com.playone.mobile.ui.firebase.v1

import com.playone.mobile.remote.model.PlayoneModel

typealias PlayoneListCallback = (model: List<PlayoneModel>) -> Unit
typealias PlayoneCallback = (model: PlayoneModel) -> Unit
typealias FirebaseErrorCallback = (code: Int, msg: String, detail: String) -> Unit