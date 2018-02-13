package com.playone.android.ui.injection.component

import android.app.Application
import com.playone.android.ui.BufferooApplication
import com.playone.android.ui.injection.module.ActivityBindingModule
import com.playone.android.ui.injection.module.ApplicationModule
import com.playone.android.ui.injection.module.NetModule
import com.playone.android.ui.injection.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = arrayOf(
        ActivityBindingModule::class,
        ApplicationModule::class,
        NetModule::class,
        AndroidSupportInjectionModule::class))
interface ApplicationComponent: AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: BufferooApplication)

    override fun inject(instance: DaggerApplication)

}
