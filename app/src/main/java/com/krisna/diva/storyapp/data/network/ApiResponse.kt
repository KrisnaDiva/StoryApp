package com.krisna.diva.storyapp.data.network

sealed class ApiResponse<out R> private constructor() {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: String) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}