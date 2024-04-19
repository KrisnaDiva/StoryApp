package com.krisna.diva.storyapp.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.krisna.diva.storyapp.databinding.ActivityRegisterBinding
import com.krisna.diva.storyapp.utils.showLoading
import com.krisna.diva.storyapp.utils.showToast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

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
            )
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressIndicator.showLoading(true)
            } else {
                binding.progressIndicator.showLoading(false)
            }
        }

        viewModel.toastMessage.observe(this) { message ->
            if (message != null) {
              this.showToast(message)
            }
        }
    }
}