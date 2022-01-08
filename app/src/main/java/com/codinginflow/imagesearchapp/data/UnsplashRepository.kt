package com.codinginflow.imagesearchapp.data

import androidx.paging.*
import com.codinginflow.imagesearchapp.api.UnsplashApi
import com.codinginflow.imagesearchapp.base.BaseRepository
import com.codinginflow.imagesearchapp.data.models.SuperPhotoBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
// @Inject constructor() - like provides method in AppModule for Dagger
// we want dagger inject this parameter and say dagger how to create UnsplashRepository
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi):BaseRepository {

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

    override fun getSearchResultsTest(query: String): Flow<PagingData<SuperPhotoBO>> =
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
        ).flow.map { pagingData ->
            pagingData.map {
                it.toSuperPhoto()
            }
        }
    // turn this Pager into stream of paging data contained livedata
}