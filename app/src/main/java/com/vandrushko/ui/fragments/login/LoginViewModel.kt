package com.vandrushko.ui.fragments.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandrushko.R
import com.vandrushko.data.DataStoreSingleton
import com.vandrushko.data.db.UserDataBase
import com.vandrushko.data.db.entity.UserResponseEntity
import com.vandrushko.data.model.UserRequest
import com.vandrushko.data.model.mapToUserDataEntity
import com.vandrushko.domain.repository.ContactsRepository
import com.vandrushko.domain.repository.UserDataHolderRepository
import com.vandrushko.ui.utils.JobState
import com.vandrushko.ui.fragments.Configs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val userDataBase: UserDataBase,
    private val userDataHolderRepository: UserDataHolderRepository
) : ViewModel() {

    private val _userStateFlow = MutableStateFlow<JobState>(JobState.Empty)
    val userStateFlow: StateFlow<JobState> = _userStateFlow

    fun loginUser(body: UserRequest) = viewModelScope.launch(Dispatchers.IO) {
        _userStateFlow.value = JobState.Loading
        try {
            login(body)
        } catch (e: Exception) {
            e.printStackTrace()
            _userStateFlow.value = JobState.Error(R.string.login_error)
        }
    }

    fun autoLoginAttempt(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        _userStateFlow.value = JobState.Loading
        try {
            val email = DataStoreSingleton.readStringData(context, Configs.EMAIL_KEY)
            val password = DataStoreSingleton.readStringData(context, Configs.PASSWORD_KEY)

            login(UserRequest(email, password))
        } catch (e: Exception) {
            e.printStackTrace()
            _userStateFlow.value = JobState.Error(R.string.autologin_error)
        }
    }

    private suspend fun login(body: UserRequest) {
        val response = contactsRepository.loginUser(body)

        if (response.code != null && response.code == 200) {
            with(response.data.user) {
                userDataBase.userDao().insetUserData(
                    UserResponseEntity(
                        id,
                        this.mapToUserDataEntity(),
                        response.data.accessToken,
                        response.data.refreshToken
                    )
                )

            }
            userDataHolderRepository
                .setUserData(response.data)
            _userStateFlow.value = JobState.Success(response.data)
        } else {
            _userStateFlow.value = JobState.Error(
                R.string.login_error
            )
        }

    }
}