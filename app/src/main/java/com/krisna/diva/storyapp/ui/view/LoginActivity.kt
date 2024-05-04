package com.krisna.diva.storyapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.storyapp.R
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.data.model.UserModel
import com.krisna.diva.storyapp.databinding.ActivityLoginBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.viewmodel.LoginViewModel
import com.krisna.diva.storyapp.utils.NetworkUtils
import com.krisna.diva.storyapp.utils.showLoading
import com.krisna.diva.storyapp.utils.showToast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (!NetworkUtils.isNetworkAvailable(this)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_description)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        finish()
                    }
                    .show()
            } else {
                viewModel.login(
                    binding.edLoginEmail.text.toString(),
                    binding.edLoginPassword.text.toString()
                ).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                binding.progressIndicator.showLoading(true)
                            }

                            is ResultState.Success -> {
                                viewModel.saveUser(
                                    UserModel(
                                        result.data.loginResult.name,
                                        binding.edLoginEmail.text.toString(),
                                        result.data.loginResult.token
                                    )
                                )
                                showToast(result.data.message)
                                binding.progressIndicator.showLoading(false)
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }

                            is ResultState.Error -> {
                                showToast(result.error)
                                binding.progressIndicator.showLoading(false)
                            }

                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }
}