package com.playone.mobile.ui.injection.component

import android.app.Application
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.repository.PlayoneRepository
import com.playone.mobile.ui.injection.module.TestActivityBindingModule
import com.playone.mobile.ui.injection.module.TestApplicationModule
import com.playone.mobile.ui.injection.scopes.PerApplication
import com.playone.mobile.ui.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        TestApplicationModule::class,
        TestActivityBindingModule::class,
        AndroidSupportInjectionModule::class])
@PerApplication
interface TestApplicationComponent : ApplicationComponent {

    fun playoneRepository(): PlayoneRepository

    fun postExecutionThread(): PostExecutionThread

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }

}