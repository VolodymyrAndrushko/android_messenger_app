package com.vandrushko.ui.fragments.pager.contacts.dialogFragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandrushko.data.model.ContactRequest
import com.vandrushko.domain.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {


    fun addContact(userId: String, accessToken: String, contactRequest: ContactRequest) = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.addContact(userId, accessToken, contactRequest)
    }
}