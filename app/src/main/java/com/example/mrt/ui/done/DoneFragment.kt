package com.example.mrt.ui.done

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mrt.databinding.FragmentDoneBinding
import com.example.mrt.model.Task
import com.example.mrt.ui.adapter.TaskAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DoneFragment : Fragment() {

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTasks()
    }

    private fun getTasks() {
        val database = Firebase.database.reference.child("tasks")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val taskList = mutableListOf<Task>()
                for (postSnapshot in snapshot.children) {
                    val task = postSnapshot.getValue(Task::class.java)
                    if (task != null && task.status == "DONE") {
                        taskList.add(task)
                    }
                }

                binding.progressBar.isVisible = false
                initRecyclerView(taskList)
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.isVisible = false
            }
        })
    }

    private fun initRecyclerView(taskList: List<Task>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = TaskAdapter(taskList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}