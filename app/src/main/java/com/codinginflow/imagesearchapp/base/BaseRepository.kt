package com.codinginflow.imagesearchapp.base

import androidx.paging.PagingData
import com.codinginflow.imagesearchapp.data.models.SuperPhotoBO
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    fun getSearchResultsTest(query: String): Flow<PagingData<SuperPhotoBO>>
}