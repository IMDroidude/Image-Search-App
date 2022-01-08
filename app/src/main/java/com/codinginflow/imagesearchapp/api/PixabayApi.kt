package com.codinginflow.imagesearchapp.api

import com.codinginflow.imagesearchapp.data.models.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

@Suppress("SpellCheckingInspection")
interface PixabayApi {

    companion object {
        const val BASE_URL = "https://pixabay.com/"
        const val PIXABAY_KEY = ""///BuildConfig.PIXABAY_KEY
    }


    @GET("api/?key=$PIXABAY_KEY")
    suspend fun searchPhoto(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PixabayResponse
}