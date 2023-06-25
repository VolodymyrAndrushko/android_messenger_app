package com.vandrushko.ui.fragments.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandrushko.data.model.UserRequest
import com.vandrushko.domain.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val contactsRepository : ContactsRepository
) : ViewModel(){

    fun registerUser(body: UserRequest){
        try {
            val response = viewModelScope.launch(Dispatchers.IO) {
                contactsRepository.registerUser(body)
            }
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}