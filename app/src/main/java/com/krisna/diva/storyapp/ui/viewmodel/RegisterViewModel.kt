package com.krisna.diva.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.krisna.diva.storyapp.data.repository.StoryRepository

class RegisterViewModel(private val repository: StoryRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =  repository.register(name, email, password)
}