package com.bangkit.manduin.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import com.bangkit.manduin.ui.onboard.OnboardingActivity
import com.bangkit.manduin.R

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        setupSplash()
    }

    private fun setupSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this@SplashscreenActivity, OnboardingActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }, TIME_SPLASH)
    }

    companion object {
        const val TIME_SPLASH = 1500L
    }
}