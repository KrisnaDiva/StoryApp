package com.krisna.diva.storyapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krisna.diva.storyapp.data.network.ApiConfig
import com.krisna.diva.storyapp.data.network.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        val call = apiService.register(name, email, password)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _toastMessage.value = response.message()
                } else {
                    _toastMessage.value = "Error: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _toastMessage.value = "Failure: ${t.message}"
            }
        })
    }
}