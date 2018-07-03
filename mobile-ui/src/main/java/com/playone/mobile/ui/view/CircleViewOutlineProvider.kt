package com.playone.mobile.ui.view

import android.graphics.Outline
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewOutlineProvider

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CircleViewOutlineProvider: ViewOutlineProvider() {

    override fun getOutline(view: View, outline: Outline) {
        outline.setOval(0, 0, view.width, view.height)
    }
}