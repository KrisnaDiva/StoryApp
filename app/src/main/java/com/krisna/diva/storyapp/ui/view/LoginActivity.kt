package com.krisna.diva.storyapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.storyapp.R
import com.krisna.diva.storyapp.data.Result
import com.krisna.diva.storyapp.data.model.UserModel
import com.krisna.diva.storyapp.databinding.ActivityLoginBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.viewmodel.LoginViewModel
import com.krisna.diva.storyapp.utils.showLoading
import com.krisna.diva.storyapp.utils.showSnackBar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.edLoginEmail.text.toString(),
                binding.edLoginPassword.text.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressIndicator.showLoading(true)
                        }

                        is Result.Success -> {
                            viewModel.saveSession(UserModel(result.data.loginResult.name, binding.edLoginEmail.text.toString(), result.data.loginResult.token))
                            MaterialAlertDialogBuilder(this)
                                .setTitle(resources.getString(R.string.yeah))
                                .setMessage(result.data.message)
                                .setPositiveButton(resources.getString(R.string.next)) { _, _ ->
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                .show()
                            binding.progressIndicator.showLoading(false)

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