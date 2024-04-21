package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.krisna.diva.storyapp.data.repository.StoryRepository

class HomeViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getAllStories() = repository.getAllStories()
}