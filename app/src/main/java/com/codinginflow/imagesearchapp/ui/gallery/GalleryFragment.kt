package com.codinginflow.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

// аннотация для фрагментов, активити и серверов (т.к. у них нет primary constructor)
@AndroidEntryPoint // for inject ViewModel property
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding : FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoStateAdapter { adapter.retry() },
                footer = UnsplashPhotoStateAdapter { adapter.retry() }
            // adapter.retry() - update PagingDataAdapter if error was
            )
        }

        // обозреваем переменную GalleryViewModel - photos (которая изменяется при изменении запроса пользователя)
        viewModel.photos.observe(viewLifecycleOwner){
            // viewLifecycleOwner.lifecycle - lifecycle view, not Fragment
            // Fragment - only container, has lifecycle longer than lifecycle view
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    binding.recyclerView.scrollToPosition(0) // при новом search - scroll to the top
                    viewModel.searchPhotos(query)
                    searchView.clearFocus() // скрываем клавиатуру
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}