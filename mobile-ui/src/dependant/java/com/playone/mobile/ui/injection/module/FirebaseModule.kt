package com.playone.mobile.ui.injection.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.playone.mobile.domain.Authenticator
import com.playone.mobile.remote.bridge.playone.PlayoneFirebase
import com.playone.mobile.ui.firebase.FirebaseAuthenticator
import com.playone.mobile.ui.firebase.v1.PlayoneFirebaseV1
import com.playone.mobile.ui.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    @PerApplication
    internal fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @PerApplication
    internal fun provideDatabaseReference() = FirebaseDatabase.getInstance().reference

    @Provides
    internal fun providePlayoneFirebase(databaseReference: DatabaseReference): PlayoneFirebase =
        PlayoneFirebaseV1(databaseReference)

    @Provides
    internal fun provideAuthenticator(firebaseAuth: FirebaseAuth): Authenticator =
        FirebaseAuthenticator(firebaseAuth)
}