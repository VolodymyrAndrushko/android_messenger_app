package com.vandrushko.ui.fragments.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandrushko.di.network.NetworkService
import com.vandrushko.data.model.UserRequest
import com.vandrushko.domain.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    fun loginUser(body: UserRequest) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = contactsRepository.loginUser(body)
            Log.d("RESPONSE", "response$response")
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
}