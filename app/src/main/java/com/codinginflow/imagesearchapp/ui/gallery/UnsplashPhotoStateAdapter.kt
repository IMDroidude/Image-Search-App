package com.codinginflow.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.imagesearchapp.databinding.UnsplashPhotoLoadStateFooterBinding

// () -> Unit - we late pass the function which does take arguments and then returns Unit
class UnsplashPhotoStateAdapter (private val ret: () -> Unit) :
    LoadStateAdapter<UnsplashPhotoStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            UnsplashPhotoLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    // для доступа к внешним переменным, в данном случае retry
    inner class LoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init{
            binding.buttonRetry.setOnClickListener {
                ret.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}