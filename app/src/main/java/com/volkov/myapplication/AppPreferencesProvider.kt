package com.volkov.myapplication

import android.content.Context
import android.content.SharedPreferences
import com.chibatching.kotpref.PreferencesProvider

// провайдер преференсов в зависимости от константы
class AppPreferencesProvider : PreferencesProvider {

    override fun get(context: Context, name: String, mode: Int): SharedPreferences {
        return context.getSharedPreferences(name, mode)
    }
}

// дефолтный провайдер преференсов
val defaultAppPreferencesProvider: PreferencesProvider = AppPreferencesProvider()