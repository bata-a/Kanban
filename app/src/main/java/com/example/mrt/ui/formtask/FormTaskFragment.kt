package com.example.mrt.ui.formtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mrt.R
import com.example.mrt.databinding.FragmentFormTaskBinding
import com.example.mrt.model.Task
import com.example.mrt.util.initToolbar
import com.example.mrt.util.showBottomSheet
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonSave.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val description = binding.editDescription.text.toString().trim()

        if (description.isEmpty()) {
            showBottomSheet(message = getString(R.string.description_empty))
            return
        }

        saveTask(description)
    }

    private fun saveTask(description: String) {
        val database = Firebase.database.reference
        val task = Task(description = description, status = "TODO")

        database.child("tasks").push().setValue(task)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showBottomSheet(message = getString(R.string.task_saved_success))
                    findNavController().popBackStack()
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