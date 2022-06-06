package com.bangkit.manduin.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import com.bangkit.manduin.R
import com.bangkit.manduin.ui.auth.AuthActivity
import com.bangkit.manduin.ui.onboard.OnboardingActivity
import com.bangkit.manduin.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashscreenActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        setupSplash()
    }

    private fun setupSplash() {
        Handler(Looper.getMainLooper())
            .postDelayed( { setupIntent() }, TIME_SPLASH)
    }

    private fun setupIntent() {
        splashViewModel.apply {
            getOpennedState().observe(this@SplashscreenActivity) { isOpenned ->
                Log.d("TEST", isOpenned.toString())
                if (isOpenned) {
                    startActivity(Intent(this@SplashscreenActivity, AuthActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashscreenActivity, OnboardingActivity::class.java))
                    finish()
                }
            }
        }
    }

    companion object {
        const val TIME_SPLASH = 1500L
    }
}