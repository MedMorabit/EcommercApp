package com.example.ecommercapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApp {
@Provides
@Singleton
fun provideFirebaseAuth()=FirebaseAuth.getInstance()
    @Provides
    @Singleton
    fun provideCloudDb()=FirebaseFirestore.getInstance()
}