package com.playone.mobile.ui.view

import android.arch.lifecycle.LifecycleObserver
import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class ToggleDebouncer<T: ToggleDebouncer.ToggleObject, CB>(
    toggleObject: T,
    long: Number = DEFAULT_DEBOUNCE_MS_TIME,
    unit: TimeUnit = TimeUnit.MILLISECONDS,
    var callback: (() -> CB)? = null
): LifecycleObserver {

    private val toggleObjWeakRef: WeakReference<T> = toggleObject.weakReference()

    internal val handler = Handler(Looper.getMainLooper())
    internal var runnable: Runnable? = null

    init {
        val delay = TimeUnit.MILLISECONDS.convert(long.toLong(), unit)
        toggleObjWeakRef.get()?.listener = {
            handler.removeCallbacks(runnable)
            runnable = newRunnable()
            handler.postDelayed(runnable, delay)
        }
    }

    internal fun newRunnable() = Runnable {
        callback?.invoke()
    }

    fun stop() {
        handler.removeCallbacks(runnable)
        toggleObjWeakRef.clear()
        runnable = null
    }

    companion object {
        const val DEFAULT_DEBOUNCE_MS_TIME = 1000
    }

    open class ToggleObject {
        var listener: OnToggledListener? = null

        fun toggle() {
            listener?.invoke()
        }
    }
}

fun <T: Any> T.weakReference() = WeakReference<T>(this)
typealias OnToggledListener = () -> Unit
