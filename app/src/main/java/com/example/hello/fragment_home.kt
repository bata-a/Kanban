package com.example.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hello.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedTaskViewModel by activityViewModels()

    private lateinit var toDoAdapter: TaskAdapter
    private lateinit var inProgressAdapter: TaskAdapter
    private lateinit var doneAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeTasks()

        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_form_task)
        }
    }

    private fun setupRecyclerViews() {
        val onItemClick: (Task) -> Unit = { task ->
            val bundle = Bundle().apply {
                putString("taskId", task.id)
            }
            findNavController().navigate(R.id.action_home_to_form_task, bundle)
        }

        toDoAdapter = TaskAdapter(onItemClick)
        binding.recyclerViewToDo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = toDoAdapter
        }

        inProgressAdapter = TaskAdapter(onItemClick)
        binding.recyclerViewInProgress.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = inProgressAdapter
        }

        doneAdapter = TaskAdapter(onItemClick)
        binding.recyclerViewDone.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = doneAdapter
        }
    }

    private fun observeTasks() {
        sharedViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            toDoAdapter.submitList(tasks.filter { it.status == TaskStatus.TO_DO }.toMutableList())
            inProgressAdapter.submitList(tasks.filter { it.status == TaskStatus.IN_PROGRESS }.toMutableList())
            doneAdapter.submitList(tasks.filter { it.status == TaskStatus.DONE }.toMutableList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}