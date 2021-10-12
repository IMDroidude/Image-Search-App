package com.codinginflow.imagesearchapp.ui.details

import android.app.DownloadManager
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.api.UnsplashApi.Companion.BASE_URL
import com.codinginflow.imagesearchapp.databinding.FragmentDetailsBinding
import java.io.File

class DetailsFragment : Fragment(R.layout.fragment_details) {

    // property args - contained navigation args
    // initialize with this property delegate by navArgs
    // pass DetailsFragmentArgs
    // now we get our photos object out of the args property
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)
        // view - inflate layout, which we pass in fragment constructor (R.layout.fragment_details)

        binding.apply {
            val photo = args.photo

            // bind photo to this layout
            Glide.with(this@DetailsFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = photo.description != null
                        buttonDownloadImage.isVisible = true
                        return false
                    }
                })
                .into(imageView)

            textViewDescription.text = photo.description

            val uri = Uri.parse(photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            textViewCreator.apply {
                text = "Photo by ${photo.user.name} on Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }

            buttonDownloadImage.setOnClickListener {
                DownloadImage(photo.urls.full)
            }
        }
    }

    fun DownloadImage(imageUrl: String) {

        val file = File(requireContext().externalMediaDirs.first(), imageUrl)

        val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setTitle("Image downloaded")
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(Uri.fromFile(file))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)

        val downloadManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireContext().getSystemService(DownloadManager::class.java)
        } else {
            error("Build.VERSION.SDK_INT < Build.VERSION_CODES.M")
        }
        downloadManager?.enqueue(request)
    }
}