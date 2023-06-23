package com.vandrushko.data.model

import com.google.gson.annotations.SerializedName

data class EditUser(
@SerializedName("name") var name: String? = null,
@SerializedName("phone") var phone: String? = null,
@SerializedName("career") var career: String? = null,
@SerializedName("address") var address: String? = null,
@SerializedName("birthday") var birthday: String? = null,
@SerializedName("facebook") var facebook: String? = null,
@SerializedName("instagram") var instagram: String? = null,
@SerializedName("twitter") var twitter: String? = null,
@SerializedName("linkedin") var linkedin: String? = null,
)

