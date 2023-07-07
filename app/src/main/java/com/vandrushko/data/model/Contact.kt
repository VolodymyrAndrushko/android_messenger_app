package com.vandrushko.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vandrushko.data.db.entity.UserDataEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    @SerializedName("email")
    val email: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("career")
    val career: String? = null,

    @SerializedName("birthday")
    val birthday: String? = null,

    @SerializedName("facebook")
    val facebook: String? = null,

    @SerializedName("instagram")
    val instagram: String? = null,

    @SerializedName("twitter")
    val twitter: String? = null,

    @SerializedName("linkedin")
    val linkedin: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("id")
    val id        : Int
) : Parcelable{
}

fun Contact.mapToUserDataEntity(): UserDataEntity {
    return UserDataEntity(
        email = this.email,
        name = this.name,
        phone = this.phone,
        address = this.address,
        career = this.career,
        birthday = this.birthday,
        facebook = this.facebook,
        instagram = this.instagram,
        twitter = this.twitter,
        linkedin = this.linkedin,
        image = this.image,
        updatedAt = this.updatedAt,
        createdAt = this.createdAt,
        id = this.id
    )
}