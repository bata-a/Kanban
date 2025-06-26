package com.example.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView // Importe TextView, se já não estiver
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createAccountButton: Button = view.findViewById(R.id.button_create_account)
        createAccountButton.setOnClickListener {

            findNavController().navigate(R.id.action_register_to_login)
        }

        val alreadyHaveAccountTextView: TextView = view.findViewById(R.id.text_view_already_have_account)
        alreadyHaveAccountTextView.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
    }
}