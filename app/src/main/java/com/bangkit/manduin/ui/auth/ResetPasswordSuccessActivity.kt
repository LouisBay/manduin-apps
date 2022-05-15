package com.bangkit.manduin.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.manduin.databinding.ActivityResetPasswordSuccessBinding

class ResetPasswordSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFinish.setOnClickListener { finish() }
    }
}