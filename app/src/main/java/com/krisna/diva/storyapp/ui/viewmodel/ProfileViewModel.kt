package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.krisna.diva.storyapp.data.repository.StoryRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: StoryRepository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getUser() = repository.getUser().asLiveData()
}