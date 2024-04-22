package com.krisna.diva.storyapp.data.repository

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.krisna.diva.storyapp.data.Result
import com.krisna.diva.storyapp.data.model.UserModel
import com.krisna.diva.storyapp.data.pref.UserPreference
import com.krisna.diva.storyapp.data.remote.response.BaseResponse
import com.krisna.diva.storyapp.data.remote.response.LoginResponse
import com.krisna.diva.storyapp.data.remote.response.StoryResponse
import com.krisna.diva.storyapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun register(name: String, email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val successResponse = apiService.register(name, email, password)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    suspend fun getAllStories() = apiService.getAllStories()

    suspend fun getDetailStory(storyId: String) = apiService.getDetailStory(storyId)

    suspend fun saveUser(user: UserModel) {
        userPreference.saveUser(user)
    }

    fun getUser(): Flow<UserModel> {
        return userPreference.getUser()
    }

    suspend fun logout() {
        userPreference.logout()
    }


    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference) =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}