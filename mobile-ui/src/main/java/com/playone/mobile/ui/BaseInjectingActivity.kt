package com.playone.mobile.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import com.playone.mobile.common.Preconditions

abstract class BaseInjectingActivity<Component> : BaseActivity() {
    private var component: Component? = null

    protected abstract fun createComponent(): Component?

    @CallSuper protected open fun onInject(component: Component) {}

    fun getComponent() = Preconditions[component]

    override fun onCreate(savedInstanceState: Bundle?) {
        component = createComponent()
        component?.apply { onInject(this) }

        super.onCreate(savedInstanceState)
    }
}