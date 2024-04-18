package com.krisna.diva.storyapp.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krisna.diva.storyapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this@RegisterActivity, "Tombol Login", Toast.LENGTH_LONG).show()
        }
        binding.btnRegister.setOnClickListener {
            Toast.makeText(this@RegisterActivity, "Tombol Register", Toast.LENGTH_LONG).show()
        }
    }
}