package com.vandrushko.data.db.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.vandrushko.data.db.entity.UserDataEntity
import com.vandrushko.data.model.Contact

class ContactTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun contactToUserData(userData: UserDataEntity): String {
        return gson.toJson(userData)
    }

    @TypeConverter
    fun jsonToUserData(json: String): UserDataEntity {
        return gson.fromJson(json, UserDataEntity::class.java)
    }
}