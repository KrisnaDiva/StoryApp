package com.krisna.diva.storyapp.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.krisna.diva.storyapp.data.network.ApiResponse
import com.krisna.diva.storyapp.databinding.ActivityRegisterBinding
import com.krisna.diva.storyapp.di.ViewModelFactory
import com.krisna.diva.storyapp.utils.showLoading
import com.krisna.diva.storyapp.utils.showToast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this@RegisterActivity, "Tombol Login", Toast.LENGTH_LONG).show()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.edRegisterName.text.toString(),
                binding.edRegisterEmail.text.toString(),
                binding.edRegisterPassword.text.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ApiResponse.Loading -> {
                            binding.progressIndicator.showLoading(true)

                        }

                        is ApiResponse.Success -> {
                            this.showToast(result.data.message)
                            binding.progressIndicator.showLoading(false)

                        }

                        is ApiResponse.Error -> {
                            this.showToast(result.error)
                            binding.progressIndicator.showLoading(false)

                        }
                    }
                }
            }
        }
    }
}