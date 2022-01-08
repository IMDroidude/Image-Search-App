package com.codinginflow.imagesearchapp.base

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T:Any>: PagingSource<Int, T>() {

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition //super.getRefreshKey(state)
    }

     override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
         val dataSource = loadDataSource()
         return LoadResult.Page(dataSource, pevPage(), nextpage())
     }
     abstract suspend fun loadDataSource(): List<T>
     abstract fun pevPage (): Int?
     abstract fun nextpage (): Int?
 }