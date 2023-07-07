package com.vandrushko.domain.repository

import com.vandrushko.data.UserDataHolder
import com.vandrushko.data.model.UserData
import javax.inject.Inject


class UserDataHolderRepository @Inject constructor(
    private val userDataHolder: UserDataHolder
) {

    fun setUserData(userData: UserData){
        this.userDataHolder.userData = userData
    }

    fun getUserData(): UserData {
        return requireNotNull(userDataHolder.userData)
    }
}