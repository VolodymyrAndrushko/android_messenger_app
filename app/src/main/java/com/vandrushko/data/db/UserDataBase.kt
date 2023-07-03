package com.vandrushko.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vandrushko.data.db.entity.UserResponseEntity
import com.vandrushko.data.db.utils.ContactTypeConverter
import com.vandrushko.data.model.UserData

@Database(entities = [UserResponseEntity::class], version = 2)
@TypeConverters(ContactTypeConverter::class)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}