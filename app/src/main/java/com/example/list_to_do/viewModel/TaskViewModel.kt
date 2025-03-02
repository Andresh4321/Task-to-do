package com.example.list_to_do.viewModel

import androidx.lifecycle.ViewModel
import com.example.list_to_do.Repositary.TaskRepositaryImpl
import com.example.list_to_do.model.TaskModel

class TaskViewModel : ViewModel() {
     val repository = TaskRepositaryImpl()

    fun addTask(task: TaskModel, onResult: (Boolean, String) -> Unit) {
        repository.addTask(task) { success, message ->
            onResult(success, message)
        }
    }

    fun updateTaskDuration(taskId: String, remainingTime: Long) {
        repository.updateDuration(taskId, remainingTime)
    }

    fun getTasks(onResult: (List<TaskModel>) -> Unit) {
        repository.getTasks(onResult)
    }

    fun updateTaskStatus(task: TaskModel, onResult: (Boolean, String) -> Unit) {
        repository.updateTaskStatus(task) { success, message ->
            onResult(success, message)
        }
    }
    }
