package com.playone.mobile.ui.playone

import android.content.Context
import android.support.design.bottomappbar.BottomAppBar
import android.support.design.bottomappbar.BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
import android.support.design.bottomappbar.BottomAppBar.FAB_ALIGNMENT_MODE_END
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.playone.mobile.ui.view.ViewHelper
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class BottomAppBarBehavior: BottomAppBar.Behavior {

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) {}

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomAppBar,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean = axes == ViewCompat.SCROLL_AXIS_VERTICAL

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomAppBar,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = max(0f, min(child.height.toFloat(), child.translationY + dy))

        if (child.translationY == child.height.toFloat()) {
            child.fabAlignmentMode = FAB_ALIGNMENT_MODE_END
        } else {
            child.fabAlignmentMode = FAB_ALIGNMENT_MODE_CENTER
        }
    }
}