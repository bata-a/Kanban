package com.example.mrt.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.example.mrt.R
import com.example.mrt.databinding.FragmentHomeBinding
import com.example.mrt.ui.doing.DoingFragment
import com.example.mrt.ui.done.DoneFragment
import com.example.mrt.ui.todo.TodoFragment
import androidx.navigation.fragment.findNavController
import com.example.mrt.ui.adapter.ViewPagerAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
        initListeners()
    }

    private fun initTabs() {
        val adapter = ViewPagerAdapter(requireActivity())

        adapter.addFragment(TodoFragment(), R.string.status_task_todo)
        adapter.addFragment(DoingFragment(), R.string.status_task_doing)
        adapter.addFragment(DoneFragment(), R.string.status_task_done)

        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 3

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(adapter.getTitle(position))
        }.attach()
    }

    private fun initListeners() {
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_formTask)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}