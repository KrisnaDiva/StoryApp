package com.krisna.diva.storyapp.di

import android.content.Context
import com.krisna.diva.storyapp.data.pref.UserPreference
import com.krisna.diva.storyapp.data.pref.dataStore
import com.krisna.diva.storyapp.data.remote.retrofit.ApiConfig
import com.krisna.diva.storyapp.data.repository.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(apiService, pref)
    }
}