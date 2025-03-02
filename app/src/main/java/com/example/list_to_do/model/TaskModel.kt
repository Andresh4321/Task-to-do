package com.example.list_to_do.model

class TaskModel (
    var taskId: String = "",
    val taskDesc: String = "",
    val duration: Long = 0,  // Store duration in milliseconds
    var remainingTime: Long = 0,
    var isCompleted: Boolean = false,
    val status:String=""
){
}

