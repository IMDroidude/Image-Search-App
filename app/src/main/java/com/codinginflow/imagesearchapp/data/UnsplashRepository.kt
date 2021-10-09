package com.codinginflow.imagesearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.codinginflow.imagesearchapp.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
// @Inject constructor() - like provides method in AppModule for Dagger
// we want dagger inject this parameter and say dagger how to create UnsplashRepository
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                // устанавливаем количество фото на страничке
                pageSize = 20,
                // параметр нужен, чтобы загрузилось больше фото в память
                // для бесперебойного перехода со странички на страничку для пользователя
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {UnsplashPagingSource(unsplashApi, query)}
        ).liveData
    // turn this Pager into stream of paging data contained livedata
}