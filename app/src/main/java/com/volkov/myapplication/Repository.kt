package com.volkov.myapplication

import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: RetrofitService
) : Repository {
    override suspend fun getEpisode(season: Int, episode: Int): Resource<EpisodeSource> {
        return try {
            Resource.Success(api.getEpisode(season = season, episode = episode))
        } catch (e: Exception) {
            Resource.Error(throwable = e)
        }
    }
}

interface Repository {
    suspend fun getEpisode(season: Int, episode: Int): Resource<EpisodeSource>
}