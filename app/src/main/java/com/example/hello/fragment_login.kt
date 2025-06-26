package com.example.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView // Certifique-se que esta linha está importada
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val loginButton: Button = view.findViewById(R.id.button_login)
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_home)
        }


        val createAccountTextView: TextView = view.findViewById(R.id.text_view_create_account)
        createAccountTextView.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }


        val forgotPasswordTextView: TextView = view.findViewById(R.id.text_view_forgot_password)
        forgotPasswordTextView.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_recover_account)
        }
    }
}