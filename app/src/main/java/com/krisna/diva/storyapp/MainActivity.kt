package com.krisna.diva.storyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.krisna.diva.storyapp.databinding.ActivityMainBinding
import com.krisna.diva.storyapp.ui.register.RegisterActivity
import com.krisna.diva.storyapp.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, WelcomeActivity::class.java))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.background = null
        navView.setupWithNavController(navController)

        binding.fabAddStory.setOnClickListener {
            Toast.makeText(this@MainActivity, "Tombol Di Klik", Toast.LENGTH_LONG).show()
        }
    }
}