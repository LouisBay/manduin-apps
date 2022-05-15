package com.bangkit.manduin.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnToLogin.setOnClickListener(this@RegisterActivity)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_to_login -> { finish() }
        }
    }
}