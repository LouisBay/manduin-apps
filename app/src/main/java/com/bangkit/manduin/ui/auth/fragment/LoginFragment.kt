package com.bangkit.manduin.ui.auth.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.FragmentLoginBinding
import com.bangkit.manduin.model.UserSessionModel
import com.bangkit.manduin.ui.main.MainActivity
import com.bangkit.manduin.utils.Helper.addTextErrorListener
import com.bangkit.manduin.utils.Helper.isEmailValid
import com.bangkit.manduin.utils.StateAuth
import com.bangkit.manduin.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private var loadingDialog: AlertDialog? = null

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
        googleSignInInit()

        auth = Firebase.auth

        binding.apply {
            btnForgotPassword.setOnClickListener(this@LoginFragment)
            btnToRegister.setOnClickListener(this@LoginFragment)
            ivGoogle.setOnClickListener(this@LoginFragment)
            btnLogin.setOnClickListener(this@LoginFragment)
        }
    }

    private fun initComponent() {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setView(R.layout.loading_dialog)
            .setCancelable(false)
        loadingDialog = builder.create()

        binding.edtPassword.addTextErrorListener(binding.textInputLayoutPassword)
        binding.edtEmail.addTextErrorListener(binding.textInputLayoutEmail)
    }

    private fun googleSignInInit() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                observeGoogleSignInResult(account.idToken!!)

            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun observeGoogleSignInResult(idToken: String) {
        loginViewModel.apply {
            loginWithGoogle(idToken)
            stateAuth.observe(viewLifecycleOwner) { state ->
                processResult(state)
            }
        }
    }

    private fun emailPasswordSignIn() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        val isValidInput = validateInput()

        if (isValidInput) {
            loginViewModel.apply {
                loginWithEmailAndPassword(email, password)
                stateAuth.observe(viewLifecycleOwner) { result ->
                    processResult(result)
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var status = true

        binding.apply {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()


            if (email.isBlank()) {
                status = false
                textInputLayoutEmail.error = resources.getString(R.string.empty_field)
            } else if (!email.isEmailValid()) {
                status = false
                textInputLayoutEmail.error = resources.getString(R.string.email_not_valid)
            }

            if (password.isBlank()) {
                status = false
                textInputLayoutPassword.error = resources.getString(R.string.empty_field)
            } else if (password.length < 6) {
                status = false
                textInputLayoutPassword.error = resources.getString(R.string.invalid_password)
            }
        }

        return status
    }

    private fun processResult(state: StateAuth?) {
        when (state) {
            is StateAuth.Loading -> { showLoading(true) }
            is StateAuth.Success -> {
                val user = auth.currentUser
                saveSession(user)

                Intent(requireContext(), MainActivity::class.java).apply {
                    startActivity(this)
                    requireActivity().finish()
                }

                Toast.makeText(requireContext(), resources.getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
            is StateAuth.Error -> {
                Toast.makeText(requireContext(), resources.getString(R.string.login_failed, state.message), Toast.LENGTH_LONG).show()
                showLoading(false)
            }
            else -> {}
        }
    }

    private fun saveSession(user: FirebaseUser?) {
        if (user != null) {
            loginViewModel.saveSession(
                UserSessionModel(
                    user.uid,
                    user.displayName.toString(),
                    user.email.toString(),
                    true
                )
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog?.show()
        else loadingDialog?.dismiss()
    }

    override fun onDestroy() {
        _binding = null
        loadingDialog = null
        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_to_register -> {
                view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            R.id.btn_forgot_password -> {
                view.findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
            }
            R.id.iv_google -> { googleSignIn() }
            R.id.btn_login -> { emailPasswordSignIn() }
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}