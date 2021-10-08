package com.codinginflow.imagesearchapp.di

import com.codinginflow.imagesearchapp.api.UnsplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// module for dagger (instructions)
@Module
// we want to have the object available in the lifetime app
// Dagger Hilt done for us (create the components) - ApplicationComponent - Scope of App
// в каком скопе мы хотим использовать объекты из модуля
@InstallIn(ApplicationComponent::class)
object AppModule {

    // this method tells dagger how to create the Retrofit object
    @Provides
    // we want only one retrofit instance (we don't want rest memory)
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit) : UnsplashApi =
        retrofit.create(UnsplashApi::class.java)
    // automatically get the Retrofit object from fun provideRetrofit()
}