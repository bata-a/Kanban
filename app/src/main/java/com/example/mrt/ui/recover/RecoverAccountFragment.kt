package com.example.mrt.ui.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mrt.R
import com.example.mrt.databinding.FragmentRecoverAccountBinding
import com.example.mrt.util.initToolbar
import com.example.mrt.util.showBottomSheet


class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonRecover.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isEmpty()) {
            showBottomSheet(message = getString(R.string.email_empty_recover_fragment))
            return
        }

        showBottomSheet(message = getString(R.string.email_send_success))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}