package com.codinginflow.imagesearchapp.data

import androidx.paging.PagingSource
import com.codinginflow.imagesearchapp.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

// класс - берем фото с сервера через UnsplashApi, помещая их в страничку
class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,

    // переменная приходит в runtime
    private val query: String

    // int - номер странички в данном случае, UnsplashPhoto - чем наполняем страничку
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        // params.key - номер странички

        return  try {
            // получаем через unsplashApi по запросу query, номеру странички - response
            // params.loadSize - количество items на страничке
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            // из response получаем лист с фотографиями
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if(position == UNSPLASH_STARTING_PAGE_INDEX) null else position-1,
                nextKey = if(photos.isEmpty()) null else position+1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}