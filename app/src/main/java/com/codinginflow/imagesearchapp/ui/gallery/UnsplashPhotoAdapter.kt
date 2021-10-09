package com.codinginflow.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.data.UnsplashPhoto
import com.codinginflow.imagesearchapp.databinding.ItemUnsplashPhotoBinding

// PagingDataAdapter - class from Paging library
// first argument - type of data we want to put into recycler view items
class UnsplashPhotoAdapter :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    // we have to inflate item layout
    // parent is just recycler view's item, this item we'll replace then
    // attachToParent (false) - we don't want to put items into the recycler view right now
    // instead Adapter Response before implement later
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotoViewHolder(binding)
        // create Holder
    }

    // what piece of data from our UnsplashPhoto object belongs into which view into UnsplashPhoto item layout
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
            // send to holder bind currentItem
        }
    }

    class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // holder inflate/bind layout
        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    //.centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textViewUserName.text = photo.user.username
            }
        }
    }

    companion object {
        // для сравнения двух объектов
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {

            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id
            // true - if two UnsplashPhoto objects represent the same item

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem == newItem
            // true if the contents with inner item change
        }
    }
}