package com.playone.mobile.ui.view.draggableView

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.playone.mobile.ui.R

class DraggableView : RelativeLayout {

    private lateinit var viewDragHelper: ViewDragHelper

    private var activePointerId = INVALID_POINTER
    private var lastTouchActionDownYPosition: Float = 0f
    private var listener: DraggableListener? = null
    private var originalX: Int = 0
    private var dragViewId: Int = 0
    private lateinit var dragView: View

    var marginBottom: Int = 0
    var originalY: Int = 0

    /**
     * Checks if the top view is minimized.
     *
     * @return true if the view is minimized.
     */
    val isMinimized: Boolean
        get() = marginBottom == height - dragView.top

    /**
     * Checks if the top view is maximized.
     *
     * @return true if the view is maximized.
     */
    val isMaximized: Boolean
        get() = dragView.top == 0

    /**
     * Check if this view is dragged over the middle of the this view.
     *
     * @return true if dragged view is over the middle of the this view or false if is below.
     */
    internal val isOverTheMiddle: Boolean
        get() {
            val inScreenHeight = height - dragView.top
            return inScreenHeight > height * 0.5
        }

    /**
     * Calculate the vertical drag range between the custom view and dragged view.
     *
     * @return the difference between the custom view height and the dragged view height.
     */
    private val verticalDragRange: Float
        get() = height.toFloat() - marginBottom

    val draggedViewHeightPlusMarginTop: Int
        get() = height - marginBottom

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initializeAttributes(attrs)
    }

    /**
     * Configure the DraggableListener notified when the view is minimized, maximized, closed to the
     * right or closed to the left.
     */
    fun setDraggableListener(listener: DraggableListener) {
        this.listener = listener
    }

    /**
     * To ensure the animation is going to work this method has been override to call
     * postInvalidateOnAnimation if the view is not settled yet.
     */
    override fun computeScroll() {
        if (!isInEditMode && viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    /**
     * Maximize the custom view applying an animation to return the view to the initial position.
     */
    fun maximize() {
        smoothSlideTo(SLIDE_TOP)
        notifyMaximizeToListener()
    }

    /**
     * Minimize the custom view applying an animation to put the top fragment on the bottom right
     * corner of the screen.
     */
    fun minimize() {
        smoothSlideTo(SLIDE_BOTTOM)
        notifyMinimizeToListener()
    }

    /**
     * Override method to intercept only touch events over the drag view and to cancel the drag when
     * the action associated to the MotionEvent is equals to ACTION_CANCEL or ACTION_UP.
     *
     * @param ev captured.
     * @return true if the view is going to process the touch event or false if not.
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isEnabled) {
            return false
        }
        when (MotionEventCompat.getActionMasked(ev) and MotionEventCompat.ACTION_MASK) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                viewDragHelper.cancel()
                return false
            }
            MotionEvent.ACTION_DOWN -> {
                val index = MotionEventCompat.getActionIndex(ev)
                activePointerId = MotionEventCompat.getPointerId(ev, index)
                if (activePointerId == INVALID_POINTER) {
                    return false
                }
            }
            else -> {
            }
        }
        val interceptTap = viewDragHelper.isViewUnder(dragView, ev.x.toInt(), ev.y.toInt())
        return viewDragHelper.shouldInterceptTouchEvent(ev)
    }

    /**
     * Override method to dispatch touch event to the dragged view.
     *
     * @param ev captured.
     * @return true if the touch event is realized over the drag or second view.
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val actionMasked = MotionEventCompat.getActionMasked(ev)
        if (actionMasked and MotionEventCompat.ACTION_MASK == MotionEvent.ACTION_DOWN) {
            activePointerId = MotionEventCompat.getPointerId(ev, actionMasked)
        }
        if (activePointerId == INVALID_POINTER) {
            return false
        }
        viewDragHelper.processTouchEvent(ev)
        return dragView?.let {
            val isViewHit = isViewHit(it, ev.x.toInt(), ev.y.toInt())
//            analyzeTouchToMaximizeIfNeeded(ev, isViewHit)

            isViewHit
        } ?: false
    }

    private fun analyzeTouchToMaximizeIfNeeded(ev: MotionEvent, isDragViewHit: Boolean) {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> lastTouchActionDownYPosition = ev.y
            MotionEvent.ACTION_UP -> {
                val clickOffset = ev.y - lastTouchActionDownYPosition
                if (shouldMaximizeOnClick(ev, clickOffset, isDragViewHit)) {
                    if (isMinimized) {
                        maximize()
                    }
                    else if (isMaximized) {
                        minimize()
                    }
                }
            }
            else -> {
            }
        }
    }

    fun shouldMaximizeOnClick(ev: MotionEvent, deltaY: Float, isDragViewHit: Boolean): Boolean {
        return (Math.abs(deltaY) < MIN_SLIDING_DISTANCE_ON_CLICK
                && ev.action != MotionEvent.ACTION_MOVE
                && isDragViewHit)
    }

    /**
     * Clone given motion event and set specified action. This method is useful, when we want to
     * cancel event propagation in child views by sending event with [ ][android.view.MotionEvent.ACTION_CANCEL]
     * action.
     *
     * @param event event to clone
     * @param action new action
     * @return cloned motion event
     */
    private fun cloneMotionEventWithAction(event: MotionEvent, action: Int): MotionEvent {
        return MotionEvent.obtain(
            event.downTime, event.eventTime, action, event.x,
            event.y, event.metaState
        )
    }

    /**
     * Override method to configure the dragged view and secondView layout properly.
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        when {
            isInEditMode -> {
            }
            else -> {
                if (originalX == 0 && originalY == 0) {
                    originalX = left
                    originalY = top
                }

                if (isMaximized) {
                    maximize()
                }

                if (isMinimized) {
                    minimize()
                }
            }
        }
    }

    /**
     * Override method to map dragged view, secondView to view objects, to configure dragged
     * view height and to initialize DragViewHelper.
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!isInEditMode) {
            findViews()
            initializeViewDragHelper()
        }
    }

    private fun findViews() {
        dragView = findViewById(dragViewId)
    }

    override fun addView(child: View) {
        if (childCount > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }

        super.addView(child)
    }

    override fun addView(child: View, index: Int) {
        if (childCount > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }

        super.addView(child, index)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        if (childCount > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }

        super.addView(child, params)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (childCount > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }

        super.addView(child, index, params)
    }

    /**
     * Calculate if one position is above any view.
     *
     * @param view to analyze.
     * @param x position.
     * @param y position.
     * @return true if x and y positions are below the view.
     */
    private fun isViewHit(view: View, x: Int, y: Int): Boolean {
        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)
        val parentLocation = IntArray(2)
        this.getLocationOnScreen(parentLocation)
        val screenX = parentLocation[0] + x
        val screenY = parentLocation[1] + y
        return (screenX >= viewLocation[0]
                && screenX < viewLocation[0] + view.width
                && screenY >= viewLocation[1]
                && screenY < viewLocation[1] + view.height)
    }

    /**
     * Initialize the viewDragHelper.
     */
    private fun initializeViewDragHelper() {
        viewDragHelper =
            ViewDragHelper.create(this,
                                  SENSITIVITY,
                                  DraggableViewCallback(
                                      this, dragView
                                  )
            )
    }

    /**
     * Initialize XML attributes.
     *
     * @param attrs to be analyzed.
     */
    private fun initializeAttributes(attrs: AttributeSet) {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.draggable_view)

        this.marginBottom = attributes.getDimensionPixelSize(
            R.styleable.draggable_view_view_margin_bottom,
            DEFAULT_TOP_VIEW_MARGIN
        )

        this.dragViewId = attributes.getResourceId(
            R.styleable.draggable_view_handler_view_id,
            R.id.v_drag_handler
        )
        attributes.recycle()
    }

    /**
     * Realize an smooth slide to an slide offset passed as argument. This method is the base of
     * maximize, minimize and close methods.
     *
     * @param slideOffset to apply
     * @return true if the view is slided.
     */
    private fun smoothSlideTo(slideOffset: Float): Boolean {
        val topBound = 0
        val y = (topBound + slideOffset * verticalDragRange).toInt()
        if (viewDragHelper.smoothSlideViewTo(dragView, x.toInt(), y)) {
            ViewCompat.postInvalidateOnAnimation(this)
            return true
        }
        return false
    }

    /**
     * Notify te view is maximized to the DraggableListener
     */
    private fun notifyMaximizeToListener() {
        listener?.onMaximized()
    }

    /**
     * Notify te view is minimized to the DraggableListener
     */
    private fun notifyMinimizeToListener() {
        listener?.onMinimized()
    }

    companion object {

        private const val DEFAULT_TOP_VIEW_MARGIN = 30
        private const val SLIDE_TOP = 0f
        private const val SLIDE_BOTTOM = 1f
        private const val MIN_SLIDING_DISTANCE_ON_CLICK = 10
        private const val SENSITIVITY = 1f
        private const val INVALID_POINTER = -1
    }
}