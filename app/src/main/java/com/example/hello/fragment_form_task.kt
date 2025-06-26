package com.example.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hello.databinding.FragmentFormTaskBinding
import android.util.Log

class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedTaskViewModel by activityViewModels()

    private var currentTask: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val statusOptions = TaskStatus.values().map { getTaskStatusDisplayName(it) }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statusOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTaskStatus.adapter = adapter

        val taskId = arguments?.getString("taskId")

        if (taskId != null) {
            binding.textViewTitle.text = "Editar Tarefa"

            sharedViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
                currentTask = tasks.find { it.id == taskId }
                currentTask?.let { task ->
                    binding.editTextTaskTitle.setText(task.title)
                    binding.editTextTaskDescription.setText(task.description)
                    val statusIndex = TaskStatus.values().indexOf(task.status)
                    if (statusIndex != -1) {
                        binding.spinnerTaskStatus.setSelection(statusIndex)
                    }
                } ?: run {
                    Toast.makeText(context, "Tarefa não encontrada.", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        } else {
            binding.textViewTitle.text = "Criar Nova Tarefa"
        }

        binding.buttonSaveTask.setOnClickListener {
            val title = binding.editTextTaskTitle.text.toString().trim()
            val description = binding.editTextTaskDescription.text.toString().trim()

            val selectedDisplayName = binding.spinnerTaskStatus.selectedItem.toString()
            val selectedStatus = getTaskStatusFromDisplayName(selectedDisplayName)

            if (title.isNotEmpty()) {
                if (currentTask != null) {
                    val updatedTask = currentTask!!.copy(
                        title = title,
                        description = description,
                        status = selectedStatus
                    )
                    sharedViewModel.updateTask(updatedTask)
                    Toast.makeText(context, "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    val newTask = Task(title = title, description = description, status = selectedStatus)
                    sharedViewModel.addTask(newTask)
                    Toast.makeText(context, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show()
                }
                findNavController().popBackStack()
            } else {
                binding.editTextTaskTitle.error = "O título da tarefa não pode ser vazio."
                Toast.makeText(context, "Por favor, insira um título para a tarefa.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancelTask.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTaskStatusDisplayName(status: TaskStatus): String {
        return when (status) {
            TaskStatus.TO_DO -> "A fazer"
            TaskStatus.IN_PROGRESS -> "Em progresso"
            TaskStatus.DONE -> "Concluído"
        }
    }

    private fun getTaskStatusFromDisplayName(displayName: String): TaskStatus {
        return when (displayName) {
            "A fazer" -> TaskStatus.TO_DO
            "Em progresso" -> TaskStatus.IN_PROGRESS
            "Concluído" -> TaskStatus.DONE
            else -> TaskStatus.TO_DO
        }
    }
}