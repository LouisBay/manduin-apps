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
import com.bangkit.manduin.databinding.FragmentRegisterBinding
import com.bangkit.manduin.model.UserSessionModel
import com.bangkit.manduin.ui.main.MainActivity
import com.bangkit.manduin.utils.Helper.isEmailValid
import com.bangkit.manduin.utils.StateAuth
import com.bangkit.manduin.viewmodel.RegisterViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private var loadingDialog: AlertDialog? = null

    private val scope = CoroutineScope(Dispatchers.Main)

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniComponent()
        googleSignInInit()

        auth = Firebase.auth

        binding.apply {
            btnToLogin.setOnClickListener(this@RegisterFragment)
            ivGoogle.setOnClickListener(this@RegisterFragment)
            btnRegister.setOnClickListener(this@RegisterFragment)
        }
    }

    private fun iniComponent() {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setView(R.layout.loading_dialog)
            .setCancelable(false)
        loadingDialog = builder.create()
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
        registerViewModel.apply {
            loginWithGoogle(idToken)
            stateAuth.observe(viewLifecycleOwner) { state ->
                processResult(state, LOGIN_TAG)
            }
        }
    }

    private fun processResult(state: StateAuth?, tag: String) {
        when (state) {
            is StateAuth.Loading -> { showLoading(true) }
            is StateAuth.Success -> {
                val message = if (tag == LOGIN_TAG) {
                    resources.getString(R.string.login_success)
                } else { resources.getString(R.string.register_success) }

                saveSession(tag)

                Intent(requireContext(), MainActivity::class.java).apply {
                    startActivity(this)
                    requireActivity().finish()
                }

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
            is StateAuth.Error -> {
                val message = if (tag == LOGIN_TAG) {
                    resources.getString(R.string.login_failed, state.message)
                } else { resources.getString(R.string.register_failed, state.message) }

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
            else -> {}
        }
    }

    private fun saveSession(tag: String) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val name = if (tag == LOGIN_TAG) currentUser.displayName else binding.edtFullname.text

            val user = UserSessionModel(
                currentUser.uid,
                name.toString(),
                currentUser.email.toString(),
                true
            )

            registerViewModel.saveSession(user)
            Log.d(TAG, user.toString())
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog?.show()
        else loadingDialog?.dismiss()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_to_login -> {
                view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            R.id.iv_google -> { googleSignIn() }
            R.id.btn_register -> { registerUser() }
        }
    }

    private fun registerUser() {
        val name = binding.edtFullname.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtConfirmPassword.text.toString()

        val isValidInput = validateInput()

        if (isValidInput) {
            registerViewModel.apply {
                registerUser(name, email, password)
                stateAuth.observe(viewLifecycleOwner) { result ->
                    processResult(result, REGISTER_TAG)
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var status = true

        binding.apply {
            val name = edtFullname.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val confirmPassword = edtConfirmPassword.text.toString()

            if (name.isBlank()) {
                status = false
                edtFullname.error = resources.getString(R.string.empty_field)
            }

            if (email.isBlank()) {
                status = false
                edtEmail.error = resources.getString(R.string.empty_field)
            } else if (!email.isEmailValid()) {
                status = false
                edtEmail.error = resources.getString(R.string.email_not_valid)
            }

            if (password.isBlank()) {
                status = false
                edtPassword.error = resources.getString(R.string.empty_field)
            } else if (password.length < 6) {
                status = false
                edtPassword.error = resources.getString(R.string.invalid_password)
            }

            if (confirmPassword.isBlank()) {
                status = false
                edtConfirmPassword.error = resources.getString(R.string.empty_field)
            } else if (confirmPassword != password) {
                status = false
                edtConfirmPassword.error = resources.getString(R.string.invalid_confirm_password)
            }
        }

        return status
    }

    override fun onDestroy() {
        _binding = null
        loadingDialog = null
        super.onDestroy()
    }

    override fun onPause() {
        scope.cancel()
        super.onPause()
    }

    companion object {
        private const val TAG = "RegisterFragment"
        private const val REGISTER_TAG = "register"
        private const val LOGIN_TAG = "login"
    }
}