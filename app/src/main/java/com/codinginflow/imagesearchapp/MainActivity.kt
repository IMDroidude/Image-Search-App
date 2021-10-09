package com.codinginflow.imagesearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

// аннотация для фрагментов, активити и серверов (т.к. у них нет primary constructor)
@AndroidEntryPoint // for inject GalleryFragment property
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}