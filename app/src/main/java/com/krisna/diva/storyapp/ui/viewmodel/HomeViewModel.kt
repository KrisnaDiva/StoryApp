package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.data.remote.response.StoryResponse
import com.krisna.diva.storyapp.data.repository.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _listStory = MutableLiveData<ResultState<StoryResponse>>()
    val listStory: MutableLiveData<ResultState<StoryResponse>> = _listStory

    init {
        getAllStories()
    }

    private fun getAllStories() {
        viewModelScope.launch {
            _listStory.postValue(ResultState.Loading)
            try {
                val tempStory = repository.getAllStories()
                if (tempStory.listStory.isEmpty()){
                    _listStory.postValue(ResultState.Empty)
                } else{
                _listStory.postValue(ResultState.Success(tempStory))
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
                _listStory.postValue(ResultState.Error(errorResponse.message))
            }
        }
    }
}