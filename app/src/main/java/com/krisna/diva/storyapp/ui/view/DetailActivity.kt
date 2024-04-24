package com.krisna.diva.storyapp.ui.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.databinding.ActivityDetailBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.viewmodel.DetailViewModel
import com.krisna.diva.storyapp.utils.showLoading
import com.krisna.diva.storyapp.utils.showToast

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Detail"
            setDisplayHomeAsUpEnabled(true)
        }

        val id = intent.getStringExtra(ID)
        id?.let {
            viewModel.updateId(it)
        }

        viewModel.storyDetail.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        binding.progressIndicator.showLoading(true)
                    }

                    is ResultState.Success -> {
                        binding.progressIndicator.showLoading(false)
                        binding.tvDetailName.text = result.data.story.name
                        binding.tvDetailDescription.text = result.data.story.description
                        Glide.with(binding.root)
                            .load(result.data.story.photoUrl)
                            .into(binding.ivDetailPhoto)
                    }

                    is ResultState.Error -> {
                        showToast(result.error)
                        binding.progressIndicator.showLoading(false)
                    }

                    else -> {
                        /* Do nothing*/
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> false
        }
    }

    companion object {
        const val ID = "id"
    }
}