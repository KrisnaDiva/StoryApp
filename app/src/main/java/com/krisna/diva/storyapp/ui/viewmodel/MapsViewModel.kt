package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.data.model.StoryModel
import com.krisna.diva.storyapp.data.StoryRepository

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    val listStoryWithLocation: LiveData<ResultState<List<StoryModel>>> = repository.getStoriesWithLocation()
}