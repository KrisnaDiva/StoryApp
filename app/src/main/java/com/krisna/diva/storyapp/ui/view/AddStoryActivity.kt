package com.krisna.diva.storyapp.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.storyapp.R
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.databinding.ActivityAddStoryBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.viewmodel.AddStoryViewModel
import com.krisna.diva.storyapp.util.NetworkUtils
import com.krisna.diva.storyapp.util.getImageUri
import com.krisna.diva.storyapp.util.reduceFileImage
import com.krisna.diva.storyapp.util.showLoading
import com.krisna.diva.storyapp.util.showToast
import com.krisna.diva.storyapp.util.uriToFile

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private val viewModel by viewModels<AddStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var newImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.currentImageUri.observe(this) { uri ->
            uri?.let {
                binding.ivPreview.setImageURI(it)
            }
        }

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }
        binding.buttonAdd.setOnClickListener {
            if (!NetworkUtils.isNetworkAvailable(this)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_description)
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                addNewStory()
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.currentImageUri.value = uri
            showImage()
        }
    }

    private fun startCamera() {
        newImageUri = getImageUri(this)
        launcherIntentCamera.launch(newImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            viewModel.currentImageUri.value = newImageUri
            showImage()
        }
    }

    private fun showImage() {
        viewModel.currentImageUri.value?.let {
            binding.ivPreview.setImageURI(it)
        }
    }

    private fun addNewStory() {
        viewModel.currentImageUri.value?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val description = binding.edAddDescription.text.toString()

            viewModel.addNewStory(imageFile, description).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            binding.progressIndicator.showLoading(true)
                        }

                        is ResultState.Success -> {
                            showToast(result.data.message)
                            binding.progressIndicator.showLoading(false)
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }

                        is ResultState.Error -> {
                            showToast(result.error)
                            binding.progressIndicator.showLoading(false)
                        }

                        else -> {}
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
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
}