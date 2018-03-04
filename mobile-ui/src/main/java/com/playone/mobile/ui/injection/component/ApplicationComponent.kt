package com.playone.mobile.ui.injection.component

import android.app.Application
import com.playone.mobile.ui.BufferooApplication
import com.playone.mobile.ui.injection.module.ActivityBindingModule
import com.playone.mobile.ui.injection.module.ApplicationModule
import com.playone.mobile.ui.injection.module.FirebaseModule
import com.playone.mobile.ui.injection.module.NetModule
import com.playone.mobile.ui.injection.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(
    modules = [
        ActivityBindingModule::class,
        ApplicationModule::class,
        NetModule::class,
        AndroidSupportInjectionModule::class,
        FirebaseModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: BufferooApplication)

    override fun inject(instance: DaggerApplication)

}
