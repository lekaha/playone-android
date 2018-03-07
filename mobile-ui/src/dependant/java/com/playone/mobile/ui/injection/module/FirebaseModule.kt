package com.playone.mobile.ui.injection.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
}