package com.codinginflow.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codinginflow.imagesearchapp.R
import dagger.hilt.android.AndroidEntryPoint

// аннотация для фрагментов, активити и серверов (т.к. у них нет primary constructor)
@AndroidEntryPoint // for inject ViewModel property
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // обозреваем переменную GalleryViewModel - photos (которая изменяется при изменении запроса пользователя)
        // viewLifecycleOwner - Fragment живет немного дольше, после уничтожения представления
        viewModel.photos.observe(viewLifecycleOwner){

        }
    }
}