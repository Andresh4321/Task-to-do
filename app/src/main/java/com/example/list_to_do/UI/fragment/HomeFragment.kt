package com.example.list_to_do.UI.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.list_to_do.UI.Activity.CreateActivity
import com.example.list_to_do.UI.Activity.DashboardActivity
import com.example.list_to_do.adapter.TaskFragmentAdapter
import com.example.list_to_do.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var adapter: TaskFragmentAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Tab titles for the ViewPager2
    private val tabTitles = arrayOf("Pending", "Completed")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the adapter for ViewPager2
        adapter = TaskFragmentAdapter(requireActivity() as DashboardActivity)
        binding.viewPager.adapter = adapter

        // Handle click for adding a new task
        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), CreateActivity::class.java)
            startActivity(intent)
        }

        // Link TabLayout and ViewPager2
        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}