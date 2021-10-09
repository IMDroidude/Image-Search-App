package com.codinginflow.imagesearchapp.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.codinginflow.imagesearchapp.data.UnsplashRepository

// @ViewModelInject constructor() - like @Inject constructor in UnsplashRepository
class GalleryViewModel @ViewModelInject constructor (
    private val unsplashRepository: UnsplashRepository
    ) : ViewModel() {

    companion object{
        private const val DEFAULT_QUERY = "cats"
    }

    // по умолчанию в currentQuery - запрос "cats"
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    // switchMap срабатывает, когда изменяется значение currentQuery
    // -> далее запуская метод unsplashRepository, передавая актуальный запрос
    // и с помощью cachedIn(viewModelScope) - отправляет запрос 1 раз, даже если мы перевернули экран, учнитожив активность
    // обозреваем переменную из GalleryFragment
    val photos = currentQuery.switchMap { queryString ->
        unsplashRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        // здесь изменяем запрос на тот, который ввел пользователь
        currentQuery.value = query
    }


}