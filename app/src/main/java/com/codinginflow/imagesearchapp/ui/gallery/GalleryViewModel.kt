package com.codinginflow.imagesearchapp.ui.gallery

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.codinginflow.imagesearchapp.data.UnsplashRepository

// @ViewModelInject constructor() - like @Inject constructor in UnsplashRepository
class GalleryViewModel @ViewModelInject constructor (
    private val unsplashRepository: UnsplashRepository,
    @Assisted state: SavedStateHandle // позволяет dagger инициировать новую переменную
    ) : ViewModel() {

    companion object{
        private const val CURRENT_QUERY = "current_query" // key
        private const val DEFAULT_QUERY = "cats"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    // switchMap срабатывает, когда изменяется значение currentQuery
    // -> далее запуская метод unsplashRepository, передавая актуальный запрос
    // и с помощью cachedIn(viewModelScope) - отправляет запрос 1 раз, даже если мы перевернули экран, учнитожив активность
    // обозреваем переменную из GalleryFragment
    val photos = currentQuery.switchMap { queryString ->
        unsplashRepository.getSearchResults(queryString).cachedIn(viewModelScope)
       /* unsplashRepository.getSearchResultsTest(queryString)
            .asLiveData(viewModelScope.coroutineContext)*/
    }

    fun searchPhotos(query: String) {
        // здесь изменяем запрос на тот, который ввел пользователь
        currentQuery.value = query
    }


}