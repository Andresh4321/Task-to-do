package com.example.list_to_do.UI.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.list_to_do.R
import com.example.list_to_do.adapter.TaskAdapter
import com.example.list_to_do.databinding.FragmentPendingBinding
import com.example.list_to_do.model.TaskModel
import com.example.list_to_do.viewModel.TaskViewModel

class PendingFragment : Fragment() {
    private var _binding: FragmentPendingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TaskAdapter
    private val tasks = mutableListOf<TaskModel>()
    private lateinit var viewModel: TaskViewModel
    private val handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            updateTaskDurations()
            handler.postDelayed(this, 1000) // Update every second
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Initialize RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TaskAdapter(tasks) { taskId ->
            markTaskAsComplete(taskId)
        }
        binding.recyclerView.adapter = adapter

        // Fetch tasks from ViewModel
        viewModel.getTasks { fetchedTasks ->
            tasks.clear()
            tasks.addAll(fetchedTasks.filter { !it.isCompleted }) // Only show pending tasks
            adapter.notifyDataSetChanged()
        }

        // Start the countdown
        handler.post(runnable)
    }

    private fun updateTaskDurations() {
        for (task in tasks) {
            if (task.remainingTime > 0) {
                task.remainingTime -= 1000 // Decrease by 1 second (1000 milliseconds)
                // Update the UI for this task
                adapter.notifyDataSetChanged() // Notify adapter to refresh the UI
            } else {
                // If the task is complete, mark it as complete
                markTaskAsComplete(task.taskId)
            }
        }
    }

    private fun markTaskAsComplete(taskId: String) {
        val task = tasks.find { it.taskId == taskId }
        task?.let {
            it.isCompleted = true // Set the task as completed
            viewModel.updateTaskStatus(it) { success, message ->
                if (success) {
                    // Remove the task from the pending list
                    tasks.remove(it)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(context, "Task marked as complete!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to update task: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable) // Stop the handler when the view is destroyed
        _binding = null
    }
}