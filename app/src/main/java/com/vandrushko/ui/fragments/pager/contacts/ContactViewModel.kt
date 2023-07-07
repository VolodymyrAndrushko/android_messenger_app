package com.vandrushko.ui.fragments.pager.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandrushko.data.LocalRepositoryData
import com.vandrushko.data.model.Contact
import com.vandrushko.data.model.Users
import com.vandrushko.data.model.UsersResponse
import com.vandrushko.domain.repository.ContactsRepository
import com.vandrushko.domain.repository.UserDataHolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val userDataHolderRepository: UserDataHolderRepository
) : ViewModel() {

    private val _contactsList = MutableLiveData<List<Contact>>()
    private val _selectedContactsList = MutableLiveData<List<Pair<Contact,Int>>>()
    private var isMultiselectModeActivated = MutableLiveData(false)

    val contactsList: LiveData<List<Contact>> = _contactsList
    val selectedContactsList: LiveData<List<Pair<Contact,Int>>> = _selectedContactsList

    init {
        viewModelScope.launch(Dispatchers.IO){
            this@ContactViewModel._contactsList.postValue(getContactsList())
        }
//        _contactsList.postValue(emptyList())

        _selectedContactsList.postValue(emptyList())
    }

    private suspend fun getContactsList(): List<Contact> {
//        return LocalRepositoryData().getLocalContactsList()

//            val userData = userDataHolderRepository.getUserData()
//            return contactsRepository.getUserContacts(
//                userData.user.id.toString(),
//                userData.accessToken)
//        }
        val userData = userDataHolderRepository.getUserData()
        val userContacts = contactsRepository
            .getUserContacts(userData.user.id.toString(),userData.accessToken)

        return listOf()

    }

    fun deleteContact(contact: Contact): Boolean {
        val list = _contactsList
        if (list.value?.contains(contact) == true) {
            val currentContacts = list.value.orEmpty().toMutableList()
            currentContacts.remove(contact)
            list.value = currentContacts
            return true
        }
        return false
    }

    fun addContact(contact: Contact, position: Int) {
        val list = _contactsList
        if (list.value?.contains(contact) == false) {
            val currentContacts = list.value.orEmpty().toMutableList()
            if (currentContacts.size < position)
                currentContacts.add(contact)
            else currentContacts.add(position, contact)

            list.value = currentContacts
        }
    }

    fun addContacts(pairsList: List<Pair<Contact,Int>>?){
        if (pairsList!=null){
            for(pair in pairsList){
                addContact(pair.first,pair.second)
            }
        }
    }

    fun deleteSelectedContact(pair: Pair<Contact, Int>): Boolean {
        val list = _selectedContactsList
        if (list.value?.contains(pair) == true) {
            val currentContacts = list.value.orEmpty().toMutableList()
            currentContacts.remove(pair)
            list.value = currentContacts
            return true
        }
        return false
    }

    fun addSelectedContact(pair: Pair<Contact, Int>) {
        val list = _selectedContactsList
        if (list.value?.contains(pair) == false) {
            val currentContacts = list.value.orEmpty().toMutableList()
            currentContacts.add(pair)
            list.value = currentContacts
        }
    }

    fun deleteSelectedContacts() {
        isMultiselectModeActivated.value = false
        for (pair in _selectedContactsList.value.orEmpty()) {
            deleteSelectedContact(pair)
            deleteContact(pair.first)
        }
        _selectedContactsList.value = emptyList()
        turnOffSelectionMode()
    }

    fun turnOnSelectionMode() {
        isMultiselectModeActivated.value = true
    }

    fun turnOffSelectionMode() {
        isMultiselectModeActivated.value = false
    }

    fun isMultiselectModeActivated(): Boolean = isMultiselectModeActivated.value ?: false
}