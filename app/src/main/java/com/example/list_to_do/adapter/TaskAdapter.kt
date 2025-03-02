package com.example.list_to_do.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.list_to_do.R
import com.example.list_to_do.model.TaskModel
import com.google.android.material.button.MaterialButton
class TaskAdapter(
    private val tasks: List<TaskModel>,
    private val onCompleteClick: (String) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskDescription: TextView = itemView.findViewById(R.id.task_description)
        val taskDuration: TextView = itemView.findViewById(R.id.task_duration)
        val btnComplete: MaterialButton = itemView.findViewById(R.id.btn_complete)

        fun bind(task: TaskModel, onCompleteClick: (String) -> Unit) {
            taskDescription.text = task.taskDesc
            taskDuration.text = formatDuration(task.remainingTime) // Show remaining time

            btnComplete.setOnClickListener {
                onCompleteClick(task.taskId) // Pass the task ID to the click listener
            }
        }

        private fun formatDuration(duration: Long): String {
            val hours = (duration / 3600000).toInt()
            val minutes = (duration % 3600000 / 60000).toInt()
            val seconds = (duration % 60000 / 1000).toInt()
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, onCompleteClick)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}