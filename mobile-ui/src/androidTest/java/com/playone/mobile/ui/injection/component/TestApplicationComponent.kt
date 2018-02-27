package com.playone.mobile.ui.injection.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.repository.BufferooRepository
import com.playone.mobile.ui.injection.module.ActivityBindingModule
import com.playone.mobile.ui.injection.module.TestActivityBindingModule
import com.playone.mobile.ui.injection.module.TestApplicationModule
import com.playone.mobile.ui.injection.scopes.PerApplication
import com.playone.mobile.ui.test.TestApplication

@Component(
    modules = [
        TestApplicationModule::class,
        TestActivityBindingModule::class,
        AndroidSupportInjectionModule::class])
@PerApplication
interface TestApplicationComponent : ApplicationComponent {

    fun bufferooRepository(): BufferooRepository

    fun postExecutionThread(): PostExecutionThread

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }

}