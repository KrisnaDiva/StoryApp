package com.krisna.diva.storyapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.storyapp.R
import com.krisna.diva.storyapp.data.network.ApiResponse
import com.krisna.diva.storyapp.databinding.ActivityRegisterBinding
import com.krisna.diva.storyapp.di.ViewModelFactory
import com.krisna.diva.storyapp.ui.login.LoginActivity
import com.krisna.diva.storyapp.utils.showLoading
import com.krisna.diva.storyapp.utils.showSnackBar

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
            startActivity(Intent(this, LoginActivity::class.java))
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
                            MaterialAlertDialogBuilder(this)
                                .setTitle(resources.getString(R.string.yeah))
                                .setMessage(result.data.message)
                                .setPositiveButton(resources.getString(R.string.next)) { _, _ ->
                                    startActivity(Intent(this, LoginActivity::class.java))
                                }
                                .show()
                            binding.progressIndicator.showLoading(false)

                        }

                        is ApiResponse.Error -> {
                            binding.root.showSnackBar(result.error)
                            binding.progressIndicator.showLoading(false)

                        }
                    }
                }
            }
        }
    }
}