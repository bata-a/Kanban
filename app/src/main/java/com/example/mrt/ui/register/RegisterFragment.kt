package com.example.mrt.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mrt.R
import com.example.mrt.databinding.FragmentRegisterBinding
import com.example.mrt.util.initToolbar
import com.example.mrt.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        auth = Firebase.auth
        initListeners()
    }

    private fun initListeners() {
        binding.buttonRegister.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        if (email.isEmpty()) {
            showBottomSheet(message = getString(R.string.email_empty_register_fragment))
            return
        }

        if (password.isEmpty()) {
            showBottomSheet(message = getString(R.string.password_empty_register_fragment))
            return
        }
        
        createAccount(email, password)
    }

    private fun createAccount(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    showBottomSheet(message = getString(R.string.user_registered_success))
                } else {
                    showBottomSheet(message = getString(R.string.error_generic))
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}