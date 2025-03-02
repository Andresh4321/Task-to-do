package com.example.list_to_do.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.list_to_do.R
import com.example.list_to_do.Repositary.UserRepositoryImpl
import com.example.list_to_do.databinding.ActivityRegistrationBinding
import com.example.list_to_do.model.UserModel
import com.example.list_to_do.viewModel.UserViewModel


class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    lateinit var UserViewModel:UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo= UserRepositoryImpl()
        UserViewModel=UserViewModel(repo)

        val termsCheckBox: CheckBox = findViewById(R.id.cbTerms)


        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }


        binding.btnRegister.setOnClickListener {
            var email = binding.email.text.toString()
            var password = binding.etPassword.text.toString()
            var username = binding.etUsername.text.toString()
            var address = binding.etAddress.text.toString()
            var contact = binding.etContact.text.toString()
            val termsAccepted = termsCheckBox.isChecked

            if (username.isEmpty() || address.isEmpty() || contact.isEmpty()|| email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (!termsAccepted) {
                Toast.makeText(this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show()
            } else {
                UserViewModel.signUp(email,password){
                        success,message,userId ->
                    if(success){
                        var userModel=UserModel(
                            userId.toString(),
                            username,address,contact,email
                        )
                        UserViewModel.addUserToDatabase(userId, userModel){
                                success, message ->
                            if(success){
                                Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_SHORT).show()
                                val intent= Intent(this@RegistrationActivity,Login_Activity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_SHORT).show()
                                Toast.makeText(this@RegistrationActivity, "Invalid details", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(this@RegistrationActivity,message, Toast.LENGTH_SHORT).show()
                        Toast.makeText(this@RegistrationActivity, "Invalid details", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}