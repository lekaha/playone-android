package com.playone.mobile.ui.firebase.ext

import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.playone.mobile.ui.firebase.UploadTaskFailureCallback
import com.playone.mobile.ui.firebase.UploadTaskSuccessCallback

fun UploadTask.addEventListener(
    scb: UploadTaskSuccessCallback = {},
    ecb: UploadTaskFailureCallback = {}
) {
    addOnFailureListener(ecb)
    addOnSuccessListener(scb)
}

fun StorageReference.putBytes(
    data: ByteArray,
    scb: UploadTaskSuccessCallback = {},
    ecb: UploadTaskFailureCallback = {})
{
    putBytes(data).addEventListener(scb, ecb)
}