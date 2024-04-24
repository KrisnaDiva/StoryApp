package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.krisna.diva.storyapp.data.repository.StoryRepository

class SplashScreenViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getUser() = repository.getUser().asLiveData()
}