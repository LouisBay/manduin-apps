package com.bangkit.manduin.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnForgotPassword.setOnClickListener(this@LoginActivity)
            btnToRegister.setOnClickListener(this@LoginActivity)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_to_register -> {
                Intent(this@LoginActivity, RegisterActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.btn_forgot_password -> {
                Intent(this@LoginActivity, ForgotPasswordActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}