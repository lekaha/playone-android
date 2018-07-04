package com.playone.mobile.ui.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.transition.Explode
import android.support.transition.TransitionValues
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewGroup

class ExplodeFadeOut: Explode() {
    init {
        propagation = null
    }
    override fun onAppear(sceneRoot: ViewGroup?, view: View?, startValues: TransitionValues?,
                          endValues: TransitionValues?): Animator {
        val explodeAnimator = super.onAppear(sceneRoot, view, startValues, endValues)
        explodeAnimator.interpolator = FastOutSlowInInterpolator()
        val fadeInAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)
        return animatorSet(explodeAnimator, fadeInAnimator)
    }
    override fun onDisappear(sceneRoot: ViewGroup?, view: View?, startValues: TransitionValues?,
                             endValues: TransitionValues?): Animator {
        val explodeAnimator = super.onDisappear(sceneRoot, view, startValues, endValues)
        explodeAnimator.interpolator = FastOutSlowInInterpolator()
        val fadeOutAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f)
        return animatorSet(explodeAnimator, fadeOutAnimator)
    }
    private fun animatorSet(explodeAnimator: Animator, fadeAnimator: Animator): AnimatorSet {
        val animatorSet = AnimatorSet()
        animatorSet.play(explodeAnimator).with(fadeAnimator)
        return animatorSet
    }
}