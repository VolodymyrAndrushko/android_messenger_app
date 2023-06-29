package com.vandrushko.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Contact(
    @SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String? = null,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String? = null,

    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    val phone: String? = null,

    @SerializedName("address")
    @ColumnInfo(name = "address")
    val address: String? = null,

    @SerializedName("career")
    @ColumnInfo(name = "career")
    val career: String? = null,

    @SerializedName("birthday")
    @ColumnInfo(name = "birthday")
    val birthday: String? = null,

    @SerializedName("facebook")
    @ColumnInfo(name = "facebook")
    val facebook: String? = null,

    @SerializedName("instagram")
    @ColumnInfo(name = "instagram")
    val instagram: String? = null,

    @SerializedName("twitter")
    @ColumnInfo(name = "twitter")
    val twitter: String? = null,

    @SerializedName("linkedin")
    @ColumnInfo(name = "linkedin")
    val linkedin: String? = null,

    @SerializedName("image")
    @ColumnInfo(name = "image")
    val image: String? = null,

    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @SerializedName("id"         )
    @ColumnInfo(name = "id")
    val id        : Int?    = null
) : Parcelable{
    @PrimaryKey(autoGenerate = true) val contactId: Int? = null
}