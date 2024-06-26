package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krisna.diva.storyapp.data.StoryRepository
import com.krisna.diva.storyapp.data.model.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            repository.saveUser(user)
        }
    }
}