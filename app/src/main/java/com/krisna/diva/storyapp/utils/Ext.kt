package com.krisna.diva.storyapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showLoading(isLoading: Boolean) {
    this.visibility = if (isLoading) View.VISIBLE else View.GONE
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}