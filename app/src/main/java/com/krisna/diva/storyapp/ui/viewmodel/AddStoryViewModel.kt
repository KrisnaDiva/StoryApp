package com.krisna.diva.storyapp.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krisna.diva.storyapp.data.StoryRepository
import java.io.File

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    val currentImageUri: MutableLiveData<Uri?> = MutableLiveData<Uri?>()

    fun addNewStory(
        file: File,
        description: String,
        latitude: Double? = null,
        longitude: Double? = null
    ) =
        repository.addNewStory(file, description, latitude, longitude)
}