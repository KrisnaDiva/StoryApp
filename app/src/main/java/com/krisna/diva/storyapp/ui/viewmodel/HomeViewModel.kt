package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.krisna.diva.storyapp.data.Result
import com.krisna.diva.storyapp.data.remote.response.ListStoryItem
import com.krisna.diva.storyapp.data.remote.response.StoryResponse
import com.krisna.diva.storyapp.data.repository.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _listStory = MutableLiveData<Result<StoryResponse>>()
    val listStory: MutableLiveData<Result<StoryResponse>> = _listStory

    init {
        getAllStories()
    }

    private fun getAllStories() {
        viewModelScope.launch {
            _listStory.postValue(Result.Loading)
            try {
                val tempUsers = repository.getAllStories()
                _listStory.postValue(Result.Success(tempUsers))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
                _listStory.postValue(Result.Error(errorResponse.message))
            }
        }
    }
}