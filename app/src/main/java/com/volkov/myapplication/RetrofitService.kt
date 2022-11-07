package com.volkov.myapplication

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/player/responce.php")
    suspend fun getEpisode(
        @Query("id") id: Int = 464,
        @Query("season") season: Int = 4,
        @Query("episode") episode: Int = 6,
        @Query("voice") voice: Int = 6
    ) : EpisodeSource
}