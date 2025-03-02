package com.example.list_to_do.UI.Activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.list_to_do.R
import com.example.list_to_do.Repositary.UserRepositoryImpl
import com.example.list_to_do.databinding.ActivityEditProfileBinding
import com.example.list_to_do.viewModel.UserViewModel
import java.io.ByteArrayOutputStream


class EditProfileActivity : AppCompatActivity() {


    lateinit var binding:ActivityEditProfileBinding
    lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    lateinit var imageView: ImageView
    var selectedImageUri: Uri? = null
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo=UserRepositoryImpl()
        userViewModel= UserViewModel(repo)

        var userId : String = userViewModel.getCurrentUser()?.uid.toString()

        imageView = findViewById(R.id.editProfileImage)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    // Display the selected image in the ImageView
                    imageView.setImageURI(it)
                    // Store the selected URI for future use (posting)
                    selectedImageUri = it
                }
            }
        }


        // Insert Image Button Click
        val changeImageButton: Button = findViewById(R.id.changeImageButton)
        changeImageButton.setOnClickListener {
            openImagePicker() // Trigger the image picker
        }


        binding.btnSave.setOnClickListener {
            val about = binding.aboutEditText.text.toString().trim()
            val image = selectedImageUri
            val name=binding.nameEditText.text.toString().trim()
            val contant=binding.numberEditText.toString().trim()


            // Convert image to Base64 if exists
            val base64Image = image?.let { uri ->
                val bitmap = uriToBitmap(uri, this)
                ImageProfile(bitmap) // Your existing conversion function
            }

            userViewModel.updateProfile(
                profileImage = base64Image,
                username=name,
                contact=contant,
                about = about,
                userId = userId
            ) { success, message ->
                if (success) {
                    Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,DashboardActivity::class.java))
                } else {
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        imagePickerLauncher.launch(intent)
    }
}

private fun uriToBitmap(uri: Uri, context: Context): Bitmap {
    return context.contentResolver.openInputStream(uri).use { inputStream ->
        BitmapFactory.decodeStream(inputStream) ?: throw IllegalArgumentException("Cannot decode bitmap")
    }
}

fun ImageProfile(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
}
