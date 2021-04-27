package com.example.dicks

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DicksApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("Abhi","On onCreate")
    }
}