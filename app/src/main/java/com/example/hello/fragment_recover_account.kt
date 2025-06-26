package com.example.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView // Importe TextView
import androidx.navigation.fragment.findNavController

class RecoverAccountFragment : Fragment(R.layout.fragment_recover_account) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sendRecoveryButton: Button = view.findViewById(R.id.button_send_recovery)
        sendRecoveryButton.setOnClickListener {
            findNavController().navigate(R.id.action_recover_account_to_login)
        }

        val backToLoginTextView: TextView = view.findViewById(R.id.text_view_back_to_login)
        backToLoginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_recover_account_to_login)
        }
    }
}