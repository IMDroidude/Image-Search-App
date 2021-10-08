package com.codinginflow.imagesearchapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// annotation - we just trigger code generation data's libraries needs
// автоматически генерирует код для dagger через hilt
@HiltAndroidApp
class ImageSearchApplication : Application() {
}