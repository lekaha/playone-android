package com.playone.mobile.ui.injection.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
    internal fun providePlayoneFirebase(
        databaseReference: DatabaseReference,
        storageReference: StorageReference): PlayoneFirebase =
        PlayoneFirebaseV1(databaseReference, storageReference)

    @Provides
    internal fun provideAuthenticator(firebaseAuth: FirebaseAuth): Authenticator =
        FirebaseAuthenticator(firebaseAuth)

    @Provides
    internal fun provideStorage() = FirebaseStorage.getInstance()

    @Provides
    internal fun provideStorageReference(firebaseStorage: FirebaseStorage) =
        firebaseStorage.reference
}