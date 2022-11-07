package com.volkov.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val episodesList = mapOf(
        1 to 6,
        2 to 13,
        3 to 16,
        4 to 16,
        5 to 16,
        6 to 16,
        7 to 16,
        8 to 16,
        9 to 16,
        10 to 22,
        11 to 16,
    )

    init {
        getEpisode(season = EpisodePref.lastSeason, episode = EpisodePref.lastEpisode)
    }

    private val _state: MutableStateFlow<EpisodeState> = MutableStateFlow(EpisodeState())
    val state: StateFlow<EpisodeState> = _state
    private val _episodeState = MutableSharedFlow<String>()
    val episodeState = _episodeState.asSharedFlow()

    private fun getEpisode(season: Int, episode: Int) {
        viewModelScope.launch {
            when (val response = repository.getEpisode(season = season, episode = episode)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        src = response.data.src
                    )
                    EpisodePref.lastEpisode = episode
                    EpisodePref.lastSeason = season
                    _episodeState.emit("Season: $season, Episode: $episode")
                }
                is Resource.Error -> {
                    _episodeState.emit(response.throwable.toString())
                }
                Resource.Loading -> {

                }
            }
        }
    }

    fun getNextEpisode() {
        EpisodePref.timePosition = 0L
        val currentSeason = EpisodePref.lastSeason
        val currentEpisode = EpisodePref.lastEpisode
        if ((episodesList[currentSeason] ?: 0) == currentEpisode) {
            if (currentSeason < episodesList.size) {
                getEpisode(season = currentSeason + 1, episode = 1)
            } else {
                viewModelScope.launch {
                    _episodeState.emit("No more episodes yet.")
                }
            }
            return
        }
        getEpisode(season = currentSeason, episode = currentEpisode + 1)
    }

    fun getPrevEpisode() {
        EpisodePref.timePosition = 0L
        val currentSeason = EpisodePref.lastSeason
        val currentEpisode = EpisodePref.lastEpisode

        if (currentEpisode == 1 && currentSeason == 1) return
        if (currentEpisode == 1) {
            getEpisode(season = currentSeason - 1, episode = episodesList[currentSeason - 1] ?: 0)
            return
        }
        getEpisode(season = currentSeason, episode = currentEpisode - 1)
    }

    fun tickerFlow(period: Long, initialDelay: Long = 0L) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
    }
}