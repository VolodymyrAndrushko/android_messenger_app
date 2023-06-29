package com.vandrushko.data.db.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.vandrushko.data.model.Contact

class ContactTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun contactToJson(contact: Contact): String {
        return gson.toJson(contact)
    }

    @TypeConverter
    fun jsonToContact(json: String): Contact {
        return gson.fromJson(json, Contact::class.java)
    }
}