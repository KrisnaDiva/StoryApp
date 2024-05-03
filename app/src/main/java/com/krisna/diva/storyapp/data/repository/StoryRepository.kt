package com.krisna.diva.storyapp.data.repository

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.data.model.StoryModel
import com.krisna.diva.storyapp.data.model.UserModel
import com.krisna.diva.storyapp.data.pref.UserPreference
import com.krisna.diva.storyapp.data.remote.response.BaseResponse
import com.krisna.diva.storyapp.data.remote.response.LoginResponse
import com.krisna.diva.storyapp.data.remote.response.StoryResponse
import com.krisna.diva.storyapp.data.remote.retrofit.ApiConfig
import com.krisna.diva.storyapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryRepository private constructor(
    private var apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.register(name, email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(email, password)
            val newApiService = ApiConfig.getApiService(successResponse.loginResult.token)
            apiService = newApiService
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun getAllStories() = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getAllStories()
            val stories = response.listStory
            if (stories.isEmpty()) {
                emit(ResultState.Empty)
            } else {
                val storyList = stories.map { story ->
                    StoryModel(
                        story.id,
                        story.name,
                        story.description,
                        story.photoUrl
                    )
                }
                emit(ResultState.Success(storyList))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun getStoriesWithLocation() = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getStoriesWithLocation()
            val stories = response.listStory
            if (stories.isEmpty()) {
                emit(ResultState.Empty)
            } else {
                val storyList = stories.map { story ->
                    StoryModel(
                        story.id,
                        story.name,
                        story.description,
                        story.photoUrl,
                        story.lat,
                        story.lon
                    )
                }
                emit(ResultState.Success(storyList))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun addNewStory(
        imageFile: File,
        description: String,
        latitude: Double? = null,
        longitude: Double? = null
    ) = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )

        val requestLatitude = latitude?.toString()?.toRequestBody("text/plain".toMediaType())
        val requestLongitude = longitude?.toString()?.toRequestBody("text/plain".toMediaType())

        try {
            val successResponse = if (requestLatitude != null && requestLongitude != null) {
                apiService.addStory(multipartBody, requestBody, requestLatitude, requestLongitude)
            } else {
                apiService.addStory(multipartBody, requestBody)
            }
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

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