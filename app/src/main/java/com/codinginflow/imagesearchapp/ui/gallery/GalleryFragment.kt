package com.codinginflow.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.data.UnsplashPhoto
import com.codinginflow.imagesearchapp.databinding.FragmentGalleryBinding
import com.codinginflow.imagesearchapp.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

// аннотация для фрагментов, активити и серверов (т.к. у них нет primary constructor)
@AndroidEntryPoint // for inject ViewModel property
class GalleryFragment : Fragment(R.layout.fragment_gallery), UnsplashPhotoAdapter.onItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding : FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter(this)

        binding.apply {
            recyclerView.addItemDecoration(SpacesItemDecoration(24))
            recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            recyclerView.setHasFixedSize(true) // если изменения адаптера не могут повлиять на размер RV
            recyclerView.itemAnimator = null // отключили анимацию объектов
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoStateAdapter { adapter.retry() },
                footer = UnsplashPhotoStateAdapter { adapter.retry() }
            // adapter.retry() - update PagingDataAdapter if error was
            )

            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        // обозреваем переменную GalleryViewModel - photos (которая изменяется при изменении запроса пользователя)
        viewModel.photos.observe(viewLifecycleOwner){
            // viewLifecycleOwner.lifecycle - lifecycle view, not Fragment
            // Fragment - only container, has lifecycle longer than lifecycle view
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading // Loading is finished (not error)
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if(loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        // GalleryFragmentDirections - автоматически сгенерированный класс плагином
        // action - из GalleryFragment в DetailsFragment передает объект Parcelable (UnsplashPhoto)
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action) // запускаем
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