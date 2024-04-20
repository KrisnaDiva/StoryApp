package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.krisna.diva.storyapp.data.model.UserModel
import com.krisna.diva.storyapp.data.repository.StoryRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: StoryRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}