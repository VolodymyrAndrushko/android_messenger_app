package com.vandrushko.data.model

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("user")
    val user: Contact,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
){
}
