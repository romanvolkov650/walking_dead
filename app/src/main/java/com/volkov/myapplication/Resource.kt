package com.volkov.myapplication

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val message: String? = null, val throwable: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}