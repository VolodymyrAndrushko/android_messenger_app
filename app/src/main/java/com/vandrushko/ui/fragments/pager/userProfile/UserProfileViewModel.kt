package com.vandrushko.ui.fragments.pager.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vandrushko.data.model.Contact
import com.vandrushko.data.model.UserData
import com.vandrushko.domain.repository.UserDataHolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userDataHolderRepository: UserDataHolderRepository
): ViewModel() {

    fun getUserData(): Contact {
        return userDataHolderRepository.getUserData().user
    }
}