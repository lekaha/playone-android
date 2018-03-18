package com.playone.mobile.ui.view

import android.animation.Animator
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.transition.Transition
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import com.playone.mobile.ext.ifFalse
import com.playone.mobile.ext.ifTrue
import java.util.ArrayList

class TransitionHelper(
    val activity: Activity,
    savedInstanceState: Bundle?,
    var hasSharedElementTransition: Boolean = false) {

    private var isAfterEnter = false
    private var isPostponeEnterTransition = false
    private val listeners = ArrayList<Listener>()
    private var isViewCreatedAlreadyCalled = false

    init {
        isAfterEnter = savedInstanceState != null
        postponeEnterTransition()
    }

    /**
     * Should be called from Activity.onResume()
     */
    fun onResume() {

        if (isAfterEnter) {
            return
        }

        if (!isViewCreatedAlreadyCalled) {
            onViewCreated()
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            onAfterEnter()
        }
        else {
            hasSharedElementTransition.ifTrue {
                activity.window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {

                    override fun onTransitionStart(transition: Transition) {

                        if (isAfterEnter()) for (listener in listeners) listener.onBeforeReturn()
                    }

                    override fun onTransitionEnd(transition: Transition) {

                        if (!isAfterEnter()) onAfterEnter()
                    }

                    override fun onTransitionCancel(transition: Transition) {

                        if (!isAfterEnter()) onAfterEnter()
                    }

                    override fun onTransitionPause(transition: Transition) {
                    }

                    override fun onTransitionResume(transition: Transition) {
                    }
                })
            }
        }
    }

    /**
     * Should be called from Activity.onBackPressed()
     */
    fun onBackPressed() {

        var isConsumed = false

        for (listener in listeners) {
            isConsumed = listener.onBeforeBack() || isConsumed
        }
        if (!isConsumed) ActivityCompat.finishAfterTransition(activity)
    }

    fun onViewCreated() {

        if (isViewCreatedAlreadyCalled) return

        isViewCreatedAlreadyCalled = true

        val contentView = activity.window.decorView.findViewById<View>(android.R.id.content)
        listeners.forEach{
            it.onBeforeViewShows(contentView)
        }

        if (!isAfterEnter()) {
            listeners.forEach{
                it.onBeforeEnter(contentView)
            }
        }

        if (isPostponeEnterTransition) {
            startPostponedEnterTransition()
        }
    }

    fun addListener(listener: Listener) {

        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {

        listeners.remove(listener)
    }

    private fun onAfterEnter() {

        listeners.forEach(Listener::onAfterEnter)
        isAfterEnter = true
    }

    fun isAfterEnter() = isAfterEnter

    private fun postponeEnterTransition() {

        isAfterEnter.ifFalse {
            ActivityCompat.postponeEnterTransition(activity)
            isPostponeEnterTransition = true
        }

    }

    private fun startPostponedEnterTransition() {

        val decor = activity.window.decorView
        decor.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

            override fun onPreDraw(): Boolean {

                decor.viewTreeObserver.removeOnPreDrawListener(this)
                ActivityCompat.startPostponedEnterTransition(activity)
                hasSharedElementTransition.ifFalse {
                    onAfterEnter()
                }
                return true
            }
        })
    }

    companion object {

        fun excludeEnterTarget(activity: Activity, targetId: Int, exclude: Boolean) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.enterTransition.excludeTarget(targetId, exclude)
            }
        }

        fun makeOptionsCompat(
            fromActivity: Activity,
            vararg sharedElements: Pair<View, String>
        ): ActivityOptionsCompat {

            var elements = sharedElements
            val list = mutableListOf(*elements)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                list.add(Pair.create(fromActivity.findViewById(android.R.id.statusBarBackground),
                                     Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME))
                list.add(Pair.create(fromActivity.findViewById(android.R.id.navigationBarBackground),
                                     Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME))
            }

            //remove any views that are null
            val iter = list.listIterator()
            while (iter.hasNext()) {
                val pair = iter.next()
                if (pair.first == null) iter.remove()
            }

            elements = list.toTypedArray()
            return ActivityOptionsCompat.makeSceneTransitionAnimation(
                fromActivity, *elements)
        }

        fun fadeThenFinish(v: View, a: Activity) {

            v.animate()  //fade out the view before finishing the activity (for cleaner L transition)
                .alpha(0f)
                .setDuration(100)
                .setListener(
                    object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {

                        }

                        override fun onAnimationEnd(animation: Animator) {
                            ActivityCompat.finishAfterTransition(a)
                        }

                        override fun onAnimationCancel(animation: Animator) {

                        }

                        override fun onAnimationRepeat(animation: Animator) {

                        }
                    }).start()
        }
    }

    fun onSaveInstanceState(outState: Bundle) {

        outState.putBoolean("isAfterEnter", isAfterEnter)
    }

    interface Listener {

        fun onBeforeViewShows(contentView: View)
        fun onBeforeEnter(contentView: View)
        fun onAfterEnter()
        fun onBeforeBack(): Boolean
        fun onBeforeReturn()
    }
}