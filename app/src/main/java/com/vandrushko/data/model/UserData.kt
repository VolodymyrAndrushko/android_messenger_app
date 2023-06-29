package com.vandrushko.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserData(
    @SerializedName("user")
    @ColumnInfo(name = "user_data")
    val user: Contact? = Contact(),
    @SerializedName("accessToken")
    @ColumnInfo(name = "accessToken")
    val accessToken: String? = null,
    @SerializedName("refreshToken")
    @ColumnInfo(name = "refreshToken")
    val refreshToken: String? = null
){
    @PrimaryKey(autoGenerate = true) var uId: Int = 1
}
