package com.example.list_to_do.Repositary

import com.example.list_to_do.model.TaskModel

interface TaskRepository {

    fun addTask(task: TaskModel, onComplete: (Boolean, String) -> Unit)

    fun updateDuration(taskId: String, remainingTime: Long)

    fun getTasks(onResult: (List<TaskModel>) -> Unit)

    fun updateTaskStatus(task: TaskModel, onComplete: (Boolean, String) -> Unit)
}