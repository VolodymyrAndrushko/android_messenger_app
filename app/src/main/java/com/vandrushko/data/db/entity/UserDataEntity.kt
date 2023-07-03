package com.vandrushko.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataEntity(
    @ColumnInfo(name = "email")
    val email: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "phone")
    val phone: String? = null,

    @ColumnInfo(name = "address")
    val address: String? = null,

    @ColumnInfo(name = "career")
    val career: String? = null,

    @ColumnInfo(name = "birthday")
    val birthday: String? = null,

    @ColumnInfo(name = "facebook")
    val facebook: String? = null,

    @ColumnInfo(name = "instagram")
    val instagram: String? = null,

    @ColumnInfo(name = "twitter")
    val twitter: String? = null,

    @ColumnInfo(name = "linkedin")
    val linkedin: String? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id        : Int
) {
}