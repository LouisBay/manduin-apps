package com.bangkit.manduin.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bangkit.manduin.R
import com.bangkit.manduin.ui.auth.AuthActivity

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        setupSplash()
    }

    private fun setupSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this@SplashscreenActivity, AuthActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }, TIME_SPLASH)
    }

    companion object {
        const val TIME_SPLASH = 1500L
    }
}