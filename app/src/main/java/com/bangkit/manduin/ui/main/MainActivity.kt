package com.bangkit.manduin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.ActivityMainBinding
import com.bangkit.manduin.ui.DetailLandmarkActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControl = navHostFragment.navController

        binding.navView.setupWithNavController(navControl)

        binding.fabScan.setOnClickListener {
            startActivity(Intent(this@MainActivity, DetailLandmarkActivity::class.java))
        }
    }
}