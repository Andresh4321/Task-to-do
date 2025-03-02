package com.example.list_to_do.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.list_to_do.Repositary.UserRepository
import com.example.list_to_do.model.UserModel
import com.google.firebase.auth.FirebaseUser

class UserViewModel(var repo:UserRepository): ViewModel() {

    private val _userProfile = MutableLiveData<UserModel?>()
    val userProfile: LiveData<UserModel?> get() = _userProfile

    fun login(email:String,password:String,callback: (Boolean, String) -> Unit){
        repo.login(email,password,callback)
    }

    fun signUp(email:String, password: String, callback:(Boolean,String,String)->Unit){
        repo.signUp(email,password,callback)
    }

    fun selectGenre(genre: String, userId: String, callback: (Boolean, String) -> Unit) {
        repo.Selectgenre(genre, userId, callback)
    }

    fun updateGenre(userId: String, genre: String, callback: (Boolean, String) -> Unit){
        repo.updateGenre(userId,genre,callback)
    }

    fun addUserToDatabase(userId: String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userId, userModel, callback)
    }
    fun addProfile(userId: String,UserModel:UserModel, callback: (Boolean, String) -> Unit){
        repo.addProfile(userId,UserModel,callback)
    }



    fun forgetPassword(username:String,email: String,callback: (Boolean, String) -> Unit){
        repo.forgetPassword(username,email,callback)
    }

    fun getSelectedGenre(userId: String, callback: (String?, Boolean, String?) -> Unit){
        repo.getSelectedGenre(userId,callback)
    }


    fun getUserProfile(userId: String) {
        repo.getUserProfile(userId) { user, success, _ ->
            _userProfile.postValue(if (success) user else null)
        }
    }

    fun getUser(userId: String, callback: (UserModel?, Boolean, String) -> Unit){
        repo.getUser(userId,callback)
    }



    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }

    fun updateProfile(username:String, contact:String,profileImage: String?, about: String, userId: String, callback: (Boolean, String) -> Unit) {
        repo.updateProfile(userId,username,contact, profileImage, about, callback) // ✅ Correct order
    }

}