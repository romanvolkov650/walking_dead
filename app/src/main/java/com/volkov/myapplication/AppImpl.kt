package com.volkov.myapplication

import android.app.Application
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppImpl : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(applicationContext)
    }
}