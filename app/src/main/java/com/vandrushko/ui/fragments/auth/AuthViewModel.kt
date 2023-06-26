package com.vandrushko.ui.fragments.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandrushko.R
import com.vandrushko.data.model.UserData
import com.vandrushko.data.model.UserRequest
import com.vandrushko.domain.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    private val _registerStateFlow = MutableStateFlow<RegisterState>(RegisterState.Empty)
    val registerState: StateFlow<RegisterState> = _registerStateFlow

    fun registerUser(body: UserRequest) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _registerStateFlow.value = RegisterState.Loading
            val response = contactsRepository.registerUser(body)
            _registerStateFlow.value =
                response.data?.let { RegisterState.Success(it) } ?: RegisterState.Error(
                    R.string.register_error
                )
        } catch (e: Exception) {
            e.printStackTrace()
            _registerStateFlow.value = RegisterState.Error(
                R.string.register_error_user_exist
            )
        }
    }

    sealed class RegisterState {
        object Empty : RegisterState()
        data class Success(val userData: UserData) : RegisterState()
        object Loading : RegisterState()
        data class Error(val error: Int) : RegisterState()
    }
}