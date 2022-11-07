package com.volkov.myapplication

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideApi(): RetrofitService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://miradres.com/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build().create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepo(api: RetrofitService) : Repository = RepositoryImpl(api)
}