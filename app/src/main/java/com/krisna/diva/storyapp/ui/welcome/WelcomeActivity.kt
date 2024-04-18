package com.krisna.diva.storyapp.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krisna.diva.storyapp.databinding.ActivityWelcomeBinding
import com.krisna.diva.storyapp.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this@WelcomeActivity, "Tombol Login", Toast.LENGTH_LONG).show()
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}