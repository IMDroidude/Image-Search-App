package com.codinginflow.imagesearchapp.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codinginflow.imagesearchapp.data.UnsplashRepository

// @ViewModelInject constructor() - like @Inject constructor in UnsplashRepository
class GalleryViewModel @ViewModelInject constructor (private val unsplashRepository: UnsplashRepository) : ViewModel() {
}