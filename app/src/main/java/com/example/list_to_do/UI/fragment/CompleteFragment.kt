package com.example.list_to_do.UI.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.list_to_do.R
import com.example.list_to_do.adapter.TaskAdapter
import com.example.list_to_do.databinding.FragmentCompleteBinding
import com.example.list_to_do.model.TaskModel
import com.example.list_to_do.viewModel.TaskViewModel
class CompleteFragment : Fragment() {
    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TaskAdapter
    private val tasks = mutableListOf<TaskModel>()
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Initialize RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TaskAdapter(tasks) { taskId ->
            // Handle task actions if needed
        }
        binding.recyclerView.adapter = adapter

        // Fetch completed tasks from ViewModel
        viewModel.getTasks { fetchedTasks ->
            tasks.clear()
            tasks.addAll(fetchedTasks.filter { it.isCompleted }) // Only show completed tasks
            adapter.notifyDataSetChanged()
        }
    }

    fun refreshCompletedTasks() {
        viewModel.getTasks { fetchedTasks ->
            tasks.clear()
            tasks.addAll(fetchedTasks.filter { it.isCompleted }) // Only show completed tasks
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}