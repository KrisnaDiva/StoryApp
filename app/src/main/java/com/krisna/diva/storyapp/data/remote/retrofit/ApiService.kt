package com.krisna.diva.storyapp.data.remote.retrofit

import com.krisna.diva.storyapp.data.remote.response.BaseResponse
import com.krisna.diva.storyapp.data.remote.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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

}