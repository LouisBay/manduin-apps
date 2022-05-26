package com.bangkit.manduin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.FragmentProfileBinding
import com.bangkit.manduin.ui.auth.AuthActivity
import com.bangkit.manduin.viewmodel.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBar(view)
        googleSignInInit()
        observeDataAndSetupView()

        auth = Firebase.auth

        binding.btnLogout.setOnClickListener { showLogoutDialog() }
    }

    private fun observeDataAndSetupView() {
        profileViewModel.getSession().observe(viewLifecycleOwner) {
            binding.apply {
                tvFullName.text = it.fullname
                tvEmailProfile.text = it.email
            }
        }
    }

    private fun googleSignInInit() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun signOut() {
        profileViewModel.setUserLoginStatusDB(auth.currentUser, false)
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            profileViewModel.deleteSession()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
        }

    }

    private fun setAppBar(view: View) {
        val titleActionBar: TextView = view.findViewById(R.id.tv_title)
        titleActionBar.text = resources.getString(R.string.profile)
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.confirm_logout))
            .setMessage(resources.getString(R.string.logout_dialog_message))
            .setIcon(R.drawable.ic_baseline_logout_24)
            .setPositiveButton("Yes") { _, _ ->
                signOut()
                Toast.makeText(requireContext(), "Logout Success", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            .setCancelable(true)
            .show()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}