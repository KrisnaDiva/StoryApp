package com.krisna.diva.storyapp.data.repository

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.krisna.diva.storyapp.data.network.ApiResponse
import com.krisna.diva.storyapp.data.network.ApiService
import com.krisna.diva.storyapp.data.network.response.RegisterResponse
import retrofit2.HttpException

class RegisterRepository private constructor(
    private val apiService: ApiService
) {

    fun register(name: String, email: String, password: String) = liveData {
        emit(ApiResponse.Loading)
        try {
            val successResponse = apiService.register(name, email, password)
            emit(ApiResponse.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(ApiResponse.Error(errorResponse.message))
        }
    }

    companion object {
        @Volatile
        private var instance: RegisterRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: RegisterRepository(apiService)
            }.also { instance = it }
    }
}