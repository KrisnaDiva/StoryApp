package com.krisna.diva.storyapp.data.remote.retrofit

import com.krisna.diva.storyapp.data.remote.response.BaseResponse
import com.krisna.diva.storyapp.data.remote.response.DetailResponse
import com.krisna.diva.storyapp.data.remote.response.LoginResponse
import com.krisna.diva.storyapp.data.remote.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getAllStories(): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") username: String
    ): DetailResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): BaseResponse
}