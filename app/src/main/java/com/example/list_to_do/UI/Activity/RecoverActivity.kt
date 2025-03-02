package com.example.list_to_do.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.list_to_do.R
import com.example.list_to_do.Repositary.UserRepositoryImpl
import com.example.list_to_do.databinding.ActivityRecoverBinding
import com.example.list_to_do.viewModel.UserViewModel

class RecoverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoverBinding
    lateinit var UserViewModel:UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo=UserRepositoryImpl()
        UserViewModel= UserViewModel(repo)

        // Ensure padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Handle Back to Login
        binding.Back.setOnClickListener {
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }

        // Handle Confirm button click
        binding.button.setOnClickListener {
            val enteredUsername = binding.etUsername.text.toString()
            val enteredEmail = binding.etEmail.text.toString()

            if (enteredUsername.isEmpty() || enteredEmail.isEmpty() ) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                UserViewModel.forgetPassword(enteredUsername,enteredEmail ){
                        success,message->if(success){
                    Toast.makeText(this@RecoverActivity, "Please check your email", Toast.LENGTH_LONG).apply {
                        setGravity(Gravity.CENTER, 0, 0)
                    }.show()
                    val intent= Intent(this@RecoverActivity,Login_Activity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@RecoverActivity, "Invalid details.Try Again", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@RecoverActivity,message, Toast.LENGTH_SHORT).show()
                }
                }
            }


        }
    }
}