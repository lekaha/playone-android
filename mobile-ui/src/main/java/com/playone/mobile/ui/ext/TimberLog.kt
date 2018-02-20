package com.playone.mobile.ui.ext

import timber.log.Timber

/** Log a verbose exception and a message that will be evaluated lazily when the message is printed */
inline fun v(message: () -> String) = log { Timber.v(message()) }

inline fun v(t: Throwable, message: () -> String) = log { Timber.v(t, message()) }
inline fun v(t: Throwable) = Timber.v(t)

/** Log a debug exception and a message that will be evaluated lazily when the message is printed */
inline fun d(message: () -> String) = log { Timber.d(message()) }

inline fun d(t: Throwable, message: () -> String) = log { Timber.d(t, message()) }
inline fun d(t: Throwable) = Timber.d(t)

/** Log an info exception and a message that will be evaluated lazily when the message is printed */
inline fun i(message: () -> String) = log { Timber.i(message()) }

inline fun i(t: Throwable, message: () -> String) = log { Timber.i(t, message()) }
inline fun i(t: Throwable) = Timber.i(t)

/** Log a warning exception and a message that will be evaluated lazily when the message is printed */
inline fun w(message: () -> String) = log { Timber.w(message()) }

inline fun w(t: Throwable, message: () -> String) = log { Timber.w(t, message()) }
inline fun w(t: Throwable) = Timber.w(t)

/** Log an error exception and a message that will be evaluated lazily when the message is printed */
inline fun e(message: () -> String) = log { Timber.e(message()) }

inline fun e(t: Throwable, message: () -> String) = log { Timber.e(t, message()) }
inline fun e(t: Throwable) = Timber.e(t)

/** Log an assert exception and a message that will be evaluated lazily when the message is printed */
inline fun wtf(message: () -> String) = log { Timber.wtf(message()) }

inline fun wtf(t: Throwable, message: () -> String) = log { Timber.wtf(t, message()) }
inline fun wtf(t: Throwable) = Timber.wtf(t)

inline fun plant(tree: () -> Timber.Tree) = log { Timber.plant(tree()) }
inline fun debugTree() = Timber.DebugTree()

@PublishedApi
internal inline fun log(block: () -> Unit) {
    if (Timber.treeCount() > 0) block()
}