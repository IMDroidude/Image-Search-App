package com.codinginflow.imagesearchapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// annotation - we just trigger code generation data's libraries needs
@HiltAndroidApp
class ImageSearchApplication : Application() {
}