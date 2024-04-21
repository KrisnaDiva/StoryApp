package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.krisna.diva.storyapp.data.repository.StoryRepository

class DetailViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getDetailStory(storyId: String) = repository.getDetailStory(storyId)
}