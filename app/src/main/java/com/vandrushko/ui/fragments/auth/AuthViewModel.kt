package com.vandrushko.ui.fragments.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.vandrushko.R
import com.vandrushko.data.DataStoreSingleton
import com.vandrushko.data.LocalRepositoryData
import com.vandrushko.data.db.UserDataBase
import com.vandrushko.data.model.UserData
import com.vandrushko.data.model.UserRequest
import com.vandrushko.domain.repository.ContactsRepository
import com.vandrushko.domain.repository.utils.JobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val userDataBase: UserDataBase
) : ViewModel() {

    private val _registerStateFlow = MutableStateFlow<JobState>(JobState.Empty)
    val registerState: StateFlow<JobState> = _registerStateFlow

    fun registerUser(body: UserRequest, context: Context, saveToDataStore: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            _registerStateFlow.value = JobState.Loading
            try {
                val response = contactsRepository.registerUser(body)
                if (saveToDataStore && body.email != null && body.password != null) {
                    DataStoreSingleton.saveLoginData(context, body.email, body.password)
                }
                if (response.data!= null){
                    userDataBase.userDao().insetUserData(response.data)
                    _registerStateFlow.value = JobState.Success(response.data)
                }
                else{
                    JobState.Error(
                        R.string.register_error
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _registerStateFlow.value = JobState.Error(
                    R.string.register_error_user_exist
                )
            }
        }
}