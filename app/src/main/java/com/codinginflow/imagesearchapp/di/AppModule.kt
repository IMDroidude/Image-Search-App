package com.codinginflow.imagesearchapp.di

import com.codinginflow.imagesearchapp.BuildConfig
import com.codinginflow.imagesearchapp.api.PixabayApi
import com.codinginflow.imagesearchapp.api.UnsplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

// module for dagger (instructions)
@Module
// we want to have the object available in the lifetime app
// Dagger Hilt done for us (create the components) - ApplicationComponent - Scope of App
// в каком скопе мы хотим использовать объекты из модуля
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun okHttpClient(): OkHttpClient {
        val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        return OkHttpClient.Builder()
            //.addInterceptor(logging)
            .build()
    }


    // this method tells dagger how to create the Retrofit object
    @Provides
    @Named("UnSplash")
    // we want only one retrofit instance (we don't want rest memory)
    @Singleton
    fun provideUnSplashRetrofit(okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Named("Pixabay")
    // we want only one retrofit instance (we don't want rest memory)
    @Singleton
    fun providePixabayRetrofit(okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(@Named("UnSplash")  retrofit: Retrofit) : UnsplashApi =
        retrofit.create(UnsplashApi::class.java)


    @Provides
    @Singleton
    fun providePixabayApi(@Named("Pixabay")  retrofit: Retrofit) : PixabayApi =
        retrofit.create(PixabayApi::class.java)
    // automatically get the Retrofit object from fun provideRetrofit()
}