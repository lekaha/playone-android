package com.playone.mobile.ui.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.os.Build
import android.util.Property
import android.view.View
import java.util.HashMap

class ViewRevealManager {

    private val targets = HashMap<View, RevealValues>()

    fun createAnimator(data: RevealValues): ObjectAnimator {

        val animator = ObjectAnimator.ofFloat(data, REVEAL, data.startRadius, data.endRadius)

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                val values = getValues(animation)
                values.clip(true)
            }

            override fun onAnimationEnd(animation: Animator) {
                val values = getValues(animation)
                values.clip(false)
                targets.remove(values.target())
            }
        })

        targets[data.target()] = data
        return animator
    }

    /**
     * @return Map of started animators
     */
    fun getTargets(): Map<View, RevealValues> = targets

    /**
     * @return True if you don't want use Android native reveal animator
     * in order to use your own custom one
     */
    fun hasCustomerRevealAnimator() = true

    /**
     * @return True if animation was started and it is still running,
     * otherwise returns False
     */
    fun isClipped(child: View): Boolean {

        val data = targets[child]
        return data != null && data.isClipping
    }

    /**
     * Applies path clipping on a canvas before drawing child,
     * you should save canvas state before transformation and
     * restore it afterwards
     *
     * @param canvas Canvas to apply clipping before drawing
     * @param child Reveal animation target
     * @return True if transformation was successfully applied on
     * referenced child, otherwise child be not the target and
     * therefore animation was skipped
     */
    fun transform(canvas: Canvas, child: View): Boolean {

        val revealData = targets[child]
        return revealData != null && revealData.applyTransformation(canvas, child)
    }

    class RevealValues(// Animation target
        internal var target: View,
        internal val centerX: Int,
        internal val centerY: Int,
        internal val startRadius: Float,
        internal val endRadius: Float
    ) {

        // Flag that indicates whether view is clipping now, mutable
        /** @return View clip status
         */
        var isClipping: Boolean = false
            internal set

        // Revealed radius
        internal var radius: Float = 0.toFloat()

        // Android Canvas is tricky, we cannot clip circles directly with Canvas API
        // but it is allowed using Path, therefore we use it :|
        internal var path = Path()

        internal var op: Region.Op = Region.Op.REPLACE

        fun radius(radius: Float) {
            this.radius = radius
        }

        /** @return current clipping radius
         */
        fun radius(): Float {
            return radius
        }

        /** @return Animating view
         */
        fun target(): View {
            return target
        }

        fun clip(clipping: Boolean) {
            this.isClipping = clipping
        }

        /** @see Canvas.clipPath
         */
        fun op(): Region.Op {
            return op
        }

        /** @see Canvas.clipPath
         */
        fun op(op: Region.Op) {
            this.op = op
        }

        /**
         * Applies path clipping on a canvas before drawing child,
         * you should save canvas state before transformation and
         * restore it afterwards
         *
         * @param canvas Canvas to apply clipping before drawing
         * @param child Reveal animation target
         * @return True if transformation was successfully  applied on
         * referenced child, otherwise child be not the target and
         * therefore animation was skipped
         */
        internal fun applyTransformation(canvas: Canvas, child: View): Boolean {

            if (child !== target || !isClipping) {
                return false
            }

            path.reset()
            // trick to applyTransformation animation, when even x & y translations are running
            path.addCircle(child.x + centerX, child.y + centerY, radius, Path.Direction.CW)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                child.invalidateOutline()
            }
            canvas.clipPath(path, op)
            return true
        }

        companion object {

            private val debugPaint = Paint(Paint.ANTI_ALIAS_FLAG)

            init {
                debugPaint.color = Color.GREEN
                debugPaint.style = Paint.Style.FILL
                debugPaint.strokeWidth = 2f
            }
        }
    }

    /**
     * Property animator. For performance improvements better to use
     * directly variable member (but it's little enhancement that always
     * caught as dangerous, let's see)
     */
    class ClipRadiusProperty internal constructor() :
        Property<RevealValues, Float>(Float::class.java, "supportCircularReveal") {

        override fun set(data: RevealValues, value: Float?) {

            data.radius(value!!)
            data.target().invalidate()
        }

        override fun get(v: RevealValues) = v.radius()
    }

    /**
     * As class name cue's it changes layer type of [View] on animation createAnimator
     * in order to improve animation smooth & performance and returns original value
     * on animation end
     */
    class ChangeViewLayerTypeAdapter(
        private val viewData: RevealValues,
        private val featuredLayerType: Int
    ) : AnimatorListenerAdapter() {

        private val originalLayerType: Int = viewData.target.layerType

        override fun onAnimationStart(animation: Animator) {

            viewData.target().setLayerType(featuredLayerType, null)
        }

        override fun onAnimationCancel(animation: Animator) {

            viewData.target().setLayerType(originalLayerType, null)
        }

        override fun onAnimationEnd(animation: Animator) {

            viewData.target().setLayerType(originalLayerType, null)
        }
    }

    interface RevealViewGroup {

        /**
         * @return Bridge between view and circular reveal animation
         */
        val viewRevealManager: ViewRevealManager
    }

    companion object {

        val REVEAL = ClipRadiusProperty()

        private fun getValues(animator: Animator) =
            (animator as ObjectAnimator).target as RevealValues
    }
}

