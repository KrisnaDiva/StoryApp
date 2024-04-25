package com.krisna.diva.storyapp.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.storyapp.R
import com.krisna.diva.storyapp.databinding.ActivitySplashScreenBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.viewmodel.SplashScreenViewModel
import com.krisna.diva.storyapp.util.NetworkUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel by viewModels<SplashScreenViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        lifecycleScope.launch {
            delay(3000)
            if (!NetworkUtils.isNetworkAvailable(this@SplashScreenActivity)) {
                MaterialAlertDialogBuilder(this@SplashScreenActivity)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_description)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        finish()
                    }
                    .show()
            } else {
                viewModel.getUser().observe(this@SplashScreenActivity) { user ->
                    if (!user.isLogin) {
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                WelcomeActivity::class.java
                            )
                        )
                    } else {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    }
                    finish()
                }
            }
        }
    }
}