package com.bangkit.manduin.ui.auth.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.FragmentForgotBinding
import com.bangkit.manduin.utils.StateAuth
import com.bangkit.manduin.viewmodel.ForgotPasswordViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!
    private var loadingDialog: AlertDialog? = null

    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()

        binding.btnSend.setOnClickListener{ sendResetPasswordEmail(view) }
    }

    private fun initComponent() {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setView(R.layout.loading_dialog)
            .setCancelable(false)
        loadingDialog = builder.create()
    }

    private fun sendResetPasswordEmail(view: View) {
        val email = binding.edtEmail.text.toString()
        forgotPasswordViewModel.apply {
            sendEmailResetPassword(email)
            stateAuth.observe(viewLifecycleOwner) { state ->
                processResult(view, state)
            }
        }
    }

    private fun processResult(view: View, state: StateAuth?) {
        when (state) {
            is StateAuth.Loading -> { showLoading(true) }
            is StateAuth.Success -> {
                view.findNavController().navigate(R.id.action_forgotFragment_to_checkEmailFragment)
                Toast.makeText(requireContext(), resources.getString(R.string.success_send), Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
            is StateAuth.Error -> {
                Toast.makeText(requireContext(), resources.getString(R.string.failed_send), Toast.LENGTH_LONG).show()
                showLoading(false)
            }
            else -> {}
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
}