package com.playone.android.ui.injection.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import com.playone.android.domain.executor.PostExecutionThread
import com.playone.android.domain.repository.BufferooRepository
import com.playone.android.ui.injection.module.ActivityBindingModule
import com.playone.android.ui.injection.module.TestApplicationModule
import com.playone.android.ui.injection.scopes.PerApplication
import com.playone.android.ui.test.TestApplication

@Component(modules = arrayOf(TestApplicationModule::class, ActivityBindingModule::class,
        AndroidSupportInjectionModule::class))
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