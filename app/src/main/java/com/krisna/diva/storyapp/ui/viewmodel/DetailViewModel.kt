package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.data.remote.response.DetailResponse
import com.krisna.diva.storyapp.data.repository.StoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _storyDetail = MutableLiveData<ResultState<DetailResponse>>()
    val storyDetail: MutableLiveData<ResultState<DetailResponse>> = _storyDetail

    private val _id = MutableStateFlow("")
    private val id: StateFlow<String> = _id

    init {
        getDetailStory()
    }

    fun updateId(newPath: String) {
        _id.value = newPath
    }

    private fun getDetailStory() {
        viewModelScope.launch {
            id.collect {
                _storyDetail.postValue(ResultState.Loading)
                try {
                    val tempDetail = repository.getDetailStory(it)
                    _storyDetail.postValue(ResultState.Success(tempDetail))
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, DetailResponse::class.java)
                    _storyDetail.postValue(ResultState.Error(errorResponse.message))
                }
            }
        }
    }
}