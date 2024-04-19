package com.krisna.diva.storyapp.ui.register

import androidx.lifecycle.ViewModel
import com.krisna.diva.storyapp.data.repository.RegisterRepository

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)
}