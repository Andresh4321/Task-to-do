package com.example.list_to_do.UI.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.list_to_do.R
import com.example.list_to_do.Repositary.TaskRepositaryImpl
import com.example.list_to_do.model.TaskModel
import com.example.list_to_do.viewModel.TaskViewModel

class CreateActivity : AppCompatActivity() {
    private lateinit var viewModel: TaskViewModel
    private lateinit var etTaskDescription: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var btnSetDuration: Button
    private lateinit var btnPost: Button

    private var selectedDuration: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        viewModel = TaskViewModel()
        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        etTaskDescription = findViewById(R.id.et_task_description)
        timePicker = findViewById(R.id.time_picker)
        btnSetDuration = findViewById(R.id.btn_set_duration)
        btnPost = findViewById(R.id.btn_post)

        timePicker.setIs24HourView(true)
    }

    private fun setupListeners() {
        btnSetDuration.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            selectedDuration = (hour * 3600L + minute * 60L) * 1000
            Toast.makeText(this, "Duration set: $hour h $minute m", Toast.LENGTH_SHORT).show()
        }

        btnPost.setOnClickListener {
            val description = etTaskDescription.text.toString().trim()

            if (description.isEmpty()) {
                Toast.makeText(this, "Please enter task description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedDuration == 0L) {
                Toast.makeText(this, "Please set duration", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newTask = TaskModel(
                taskDesc = description,
                duration = selectedDuration,
                remainingTime = selectedDuration
            )

            viewModel.addTask(newTask) { success, message ->
                if (success) {
                    Toast.makeText(this, "Task created!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}