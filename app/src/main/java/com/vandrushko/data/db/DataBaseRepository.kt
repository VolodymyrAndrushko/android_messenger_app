package com.vandrushko.data.db

import javax.inject.Inject

class DataBaseRepository @Inject constructor(
    private val userDao: UserDao
) {
}