package com.codinginflow.imagesearchapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codinginflow.imagesearchapp.api.PixabayApi
import com.codinginflow.imagesearchapp.data.models.Hit
import retrofit2.HttpException
import java.io.IOException

private const val PIXABAY_STARTING_PAGE_INDEX = 1

class PixabayPagingSource(
    private val pixabayApi: PixabayApi,
    private val query: String
) : PagingSource<Int, Hit>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        val position = params.key ?: PIXABAY_STARTING_PAGE_INDEX

        return try {
            val response = pixabayApi.searchPhoto(query, position, params.loadSize)
            val images = response.hits

            LoadResult.Page(
                data = images,
                prevKey = if (position == PIXABAY_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}