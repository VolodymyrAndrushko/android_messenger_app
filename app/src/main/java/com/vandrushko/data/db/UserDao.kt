package com.vandrushko.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vandrushko.data.model.UserData

@Dao
interface UserDao {
    @Query("SELECT * FROM userdata")
    fun getAllUsers(): List<UserData>

    @Insert
    fun insetUserData(userData : UserData)

    @Delete
    fun deleteUserData(userData: UserData)
}