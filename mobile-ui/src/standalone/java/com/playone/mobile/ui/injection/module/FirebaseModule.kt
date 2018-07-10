package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.Authenticator
import com.playone.mobile.remote.bridge.playone.PlayoneFirebase
import com.playone.mobile.ui.firebase.StandaloneAuthenticator
import com.playone.mobile.ui.firebase.v1.StandaloneFirebaseV1
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    internal fun providePlayoneFirebase(): PlayoneFirebase = StandaloneFirebaseV1()

    @Provides
    internal fun provideAuthenticator(): Authenticator = StandaloneAuthenticator()

    @Provides
    internal fun provideStorage() = StandaloneFirebaseStorageV1()
}