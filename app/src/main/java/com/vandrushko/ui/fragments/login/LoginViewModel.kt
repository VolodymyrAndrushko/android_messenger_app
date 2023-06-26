package com.vandrushko.ui.fragments.login

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
class LoginViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    private val _userStateFlow = MutableStateFlow<LoginState>(LoginState.Empty)
    val userStateFlow : StateFlow<LoginState> = _userStateFlow

    fun loginUser(body: UserRequest) = viewModelScope.launch(Dispatchers.IO) {

        try {
            _userStateFlow.value = LoginState.Loading
            val response = contactsRepository.loginUser(body)
            _userStateFlow.value = response.data?.let { LoginState.Success(it) } ?: LoginState.Error(
                R.string.login_error)

        }
        catch (e: Exception){
            e.printStackTrace()
            _userStateFlow.value = LoginState.Error(R.string.login_error)
        }
    }

    sealed class LoginState{
        data class Success(val userData: UserData): LoginState()
        object Loading: LoginState()
        data class Error(val error: Int): LoginState()
        object Empty: LoginState()
    }
}