package com.volkov.myapplication

import com.chibatching.kotpref.KotprefModel

object EpisodePref : KotprefModel(preferencesProvider = defaultAppPreferencesProvider) {
    var lastSeason by intPref(1)
    var lastEpisode by intPref(1)
    var timePosition by longPref(0)
}