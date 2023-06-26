package com.vandrushko.data.model

import com.google.gson.annotations.SerializedName
data class UserData(
    @SerializedName("user") val user: Contact? = Contact(),
    @SerializedName("accessToken") val accessToken: String? = null,
    @SerializedName("refreshToken") val refreshToken: String? = null
)
