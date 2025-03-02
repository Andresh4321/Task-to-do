package com.example.list_to_do.model

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Email

data class UserModel(
    var userId:String="",
    var username:String="",
    var address:String="",
    var contact:String="",
    var profile: String? ="",
    var email: String="",
    var genre:String="",
    var about:String=""

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(username)
        parcel.writeString(address)
        parcel.writeString(contact)
        parcel.writeString(profile)
        parcel.writeString(email)
        parcel.writeString(genre)
        parcel.writeString(about)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}