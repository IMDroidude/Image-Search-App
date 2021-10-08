package com.codinginflow.imagesearchapp.data

import com.codinginflow.imagesearchapp.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
// @Inject constructor() - like provides method in AppModule for Dagger
// we want dagger inject this parameter and say dagger how to create UnsplashRepository
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {
}