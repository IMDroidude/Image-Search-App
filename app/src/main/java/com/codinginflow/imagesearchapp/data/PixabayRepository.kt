package com.codinginflow.imagesearchapp.data

import androidx.paging.PagingData
import com.codinginflow.imagesearchapp.base.BaseRepository
import com.codinginflow.imagesearchapp.data.models.SuperPhotoBO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixabayRepository @Inject constructor():BaseRepository{
    override fun getSearchResultsTest(query: String): Flow<PagingData<SuperPhotoBO>> {
        TODO("Not yet implemented")
    }
}