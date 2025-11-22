package com.example.mrt.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mrt.R
import com.example.mrt.databinding.FragmentLoginBinding
import com.example.mrt.util.showBottomSheet
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initListeners()
    }

    private fun initListeners() {
        binding.textCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        binding.textRecoverAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_recover)
        }

        binding.buttonLogin.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        if (email.isEmpty()) {
            showBottomSheet(message = getString(R.string.email_empty))
            return
        }

        if (password.isEmpty()) {
            showBottomSheet(message = getString(R.string.password_empty))
            return
        }

        login(email, password)
    }

    private fun login(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_login_to_home)
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