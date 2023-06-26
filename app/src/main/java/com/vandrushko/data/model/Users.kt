package com.vandrushko.data.model

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("users") var userData: ArrayList<UserData> = arrayListOf()

)
