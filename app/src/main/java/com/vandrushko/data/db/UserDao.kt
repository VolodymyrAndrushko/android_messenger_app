package com.vandrushko.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vandrushko.data.db.entity.UserResponseEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_response_data WHERE uId = :id")
    fun getUserById(id: String): UserResponseEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetUserData(userResponse : UserResponseEntity)

}