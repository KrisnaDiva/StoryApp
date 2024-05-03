package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.data.model.StoryModel
import com.krisna.diva.storyapp.data.StoryRepository
import com.krisna.diva.storyapp.data.remote.response.ListStoryItem
import com.krisna.diva.storyapp.data.remote.response.StoryResponse

class HomeViewModel(private val repository: StoryRepository) : ViewModel() {
    val listStory:  LiveData<PagingData<StoryModel>> = repository.getStories().cachedIn(viewModelScope)
}