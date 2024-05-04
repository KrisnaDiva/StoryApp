package com.krisna.diva.storyapp.di

import android.content.Context
import com.krisna.diva.storyapp.data.StoryRepository
import com.krisna.diva.storyapp.data.local.room.StoryDatabase
import com.krisna.diva.storyapp.data.pref.UserPreference
import com.krisna.diva.storyapp.data.pref.dataStore
import com.krisna.diva.storyapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(database, apiService, pref)
    }
}