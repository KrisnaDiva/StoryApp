package com.krisna.diva.storyapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.krisna.diva.storyapp.data.Result
import com.krisna.diva.storyapp.databinding.ActivityRegisterBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.viewmodel.RegisterViewModel
import com.krisna.diva.storyapp.utils.showLoading
import com.krisna.diva.storyapp.utils.showSnackBar
import com.krisna.diva.storyapp.utils.showToast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.edRegisterName.text.toString(),
                binding.edRegisterEmail.text.toString(),
                binding.edRegisterPassword.text.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressIndicator.showLoading(true)
                        }

                        is Result.Success -> {
                            showToast(result.data.message)
                            binding.progressIndicator.showLoading(false)
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }

                        is Result.Error -> {
                            binding.root.showSnackBar(result.error)
                            binding.progressIndicator.showLoading(false)
                        }
                    }
                }
            }
        }
    }
}