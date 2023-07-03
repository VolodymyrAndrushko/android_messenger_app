package com.vandrushko.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_response_data")
data class UserResponseEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "uId")
    val uId: Int,
    @Embedded()
    val user: UserDataEntity,
    @ColumnInfo(name = "accessToken")
    val accessToken: String? = null,
    @ColumnInfo(name = "refreshToken")
    val refreshToken: String? = null
){

}