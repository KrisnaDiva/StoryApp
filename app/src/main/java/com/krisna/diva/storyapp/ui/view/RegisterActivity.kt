package com.krisna.diva.storyapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.storyapp.R
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.databinding.ActivityRegisterBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.viewmodel.RegisterViewModel
import com.krisna.diva.storyapp.utils.NetworkUtils
import com.krisna.diva.storyapp.utils.showLoading
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
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            if (!NetworkUtils.isNetworkAvailable(this)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_description)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        finish()
                    }
                    .show()
            } else {
                viewModel.register(
                    binding.edRegisterName.text.toString(),
                    binding.edRegisterEmail.text.toString(),
                    binding.edRegisterPassword.text.toString()
                ).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                binding.progressIndicator.showLoading(true)
                            }

                            is ResultState.Success -> {
                                showToast(result.data.message)
                                binding.progressIndicator.showLoading(false)
                                startActivity(Intent(this, LoginActivity::class.java))
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