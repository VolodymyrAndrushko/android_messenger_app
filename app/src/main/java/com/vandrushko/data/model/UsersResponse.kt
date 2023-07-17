package com.vandrushko.data.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("code") val code: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: Users = Users()
)
