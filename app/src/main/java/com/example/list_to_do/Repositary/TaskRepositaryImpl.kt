package com.example.list_to_do.Repositary

import android.util.Log
import com.example.list_to_do.model.TaskModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TaskRepositaryImpl: TaskRepository {
    val database = FirebaseDatabase.getInstance().reference.child("tasks")




    override fun addTask(task: TaskModel, onComplete: (Boolean, String) -> Unit) {


        val taskRef = database.push()
        task.taskId = taskRef.key!!
        taskRef.setValue(task)
            .addOnSuccessListener { onComplete(true, task.taskId) }
            .addOnFailureListener { onComplete(false, it.message.toString()) }
    }


    override fun updateDuration(taskId: String, remainingTime: Long) {
        database.child(taskId).child("remainingTime").setValue(remainingTime)
    }

    override fun getTasks(onResult: (List<TaskModel>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val tasks = mutableListOf<TaskModel>()
                    for (eachData in snapshot.children) {
                        val model = eachData.getValue(TaskModel::class.java)
                        if (model != null) {
                            tasks.add(model)
                        }
                    }
                    // Call the onResult callback with the fetched tasks
                    onResult(tasks)
                } else {
                    // If no tasks exist, return an empty list
                    onResult(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TaskRepository", "Error fetching tasks: ${error.message}")
                // You might want to handle the error case here as well
                onResult(emptyList()) // Return an empty list on error
            }
        })
    }
    override fun updateTaskStatus(task: TaskModel, onComplete: (Boolean, String) -> Unit) {
        val taskRef = database.child(task.taskId) // Reference the existing task by taskId
        taskRef.child("completed").setValue(task.isCompleted) // Update the isCompleted field
            .addOnSuccessListener { onComplete(true, "Task updated successfully") }
            .addOnFailureListener { onComplete(false, it.message.toString()) }
    }


}