package com.vandrushko.data

import com.vandrushko.data.model.UserData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataHolder @Inject constructor(

){
    var userData: UserData? = null
}