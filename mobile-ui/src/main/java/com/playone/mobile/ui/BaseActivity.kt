package com.playone.mobile.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.playone.mobile.ui.ext.debugTree
import com.playone.mobile.ui.ext.plant
import com.playone.mobile.ui.view.ViewRevealManager
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity: DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        plant { debugTree() }
        setContentView(getLayoutId())
        super.onCreate(savedInstanceState)
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int


    private fun createCircularReveal(
        view: View,
        centerX: Int,
        centerY: Int,
        startRadius: Float,
        endRadius: Float
    ): Animator {

        return createCircularReveal(view, centerX, centerY, startRadius, endRadius,
                                    View.LAYER_TYPE_SOFTWARE)
    }

    private fun createCircularReveal(
        view: View,
        centerX: Int,
        centerY: Int,
        startRadius: Float,
        endRadius: Float,
        layerType: Int
    ): Animator {

        if (Build.VERSION_CODES.LOLLIPOP <= android.os.Build.VERSION.SDK_INT) {
            return createCircularRevealApi16(view, centerX, centerY,startRadius, endRadius)
        }

        val viewData =
            ViewRevealManager.RevealValues(view, centerX, centerY, startRadius, endRadius)
        val animator = ViewRevealManager().createAnimator(viewData)

        if (layerType != view.layerType) {
            animator.addListener(ViewRevealManager.ChangeViewLayerTypeAdapter(viewData, layerType))
        }

        return animator
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createCircularRevealApi16(
        view: View,
        centerX: Int,
        centerY: Int,
        startRadius: Float,
        endRadius: Float
    ) = android.view.ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
                                                             startRadius, endRadius)

    protected fun animateRevealShow(viewRoot: View, x: Int, y: Int, duration: Long) {

        val radius =
            Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0))
                .toInt() //hypotenuse to top left

        val anim = createCircularReveal(viewRoot, x, y, 0f, radius.toFloat())
        viewRoot.visibility = View.VISIBLE
        anim.interpolator = DecelerateInterpolator()
        anim.duration = duration
        anim.start()
    }

    protected fun animateRevealHide(viewRoot: View, x: Int, y: Int, duration: Long) {

        val radius =
            Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0))
                .toInt() //hypotenuse to top left

        val anim = createCircularReveal(viewRoot, x, y, radius.toFloat(), 0f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                viewRoot.visibility = View.INVISIBLE
            }
        })

        anim.interpolator = DecelerateInterpolator()
        anim.duration = duration
        anim.start()
    }
}