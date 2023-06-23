package com.vandrushko.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class Contact(
    @SerializedName("email") var email: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("career") var career: String? = null,
    @SerializedName("birthday") var birthday: String? = null,
    @SerializedName("facebook") var facebook: String? = null,
    @SerializedName("instagram") var instagram: String? = null,
    @SerializedName("twitter") var twitter: String? = null,
    @SerializedName("linkedin") var linkedin: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("id"         ) var id        : Int?    = null
) : Parcelable