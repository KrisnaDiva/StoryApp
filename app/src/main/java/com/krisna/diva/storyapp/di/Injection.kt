package com.krisna.diva.storyapp.di

import com.krisna.diva.storyapp.data.network.ApiConfig
import com.krisna.diva.storyapp.data.repository.RegisterRepository

object Injection {
    fun provideRepository(): RegisterRepository {
        val apiService = ApiConfig.getApiService()
        return RegisterRepository.getInstance(apiService)
    }
}