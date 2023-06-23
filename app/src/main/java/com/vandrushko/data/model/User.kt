package com.vandrushko.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user") var user: Contact? = Contact(),
    @SerializedName("accessToken") var accessToken: String? = null,
    @SerializedName("refreshToken") var refreshToken: String? = null
)
