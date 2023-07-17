package com.vandrushko.ui.fragments.pager.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandrushko.data.model.Contact
import com.vandrushko.data.model.ContactRequest
import com.vandrushko.data.model.UserData
import com.vandrushko.domain.repository.ContactsRepository
import com.vandrushko.domain.repository.UserDataHolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    userDataHolderRepository: UserDataHolderRepository
) : ViewModel() {

    private val _contactsList = MutableLiveData<List<Contact>>()
    private val _selectedContactsList = MutableLiveData<List<Pair<Contact,Int>>>()
    private var isMultiselectModeActivated = MutableLiveData(false)

    private val userData: UserData = userDataHolderRepository.getUserData()

    val contactsList: LiveData<List<Contact>> = _contactsList
    val selectedContactsList: LiveData<List<Pair<Contact,Int>>> = _selectedContactsList

    init {
        viewModelScope.launch(Dispatchers.IO){
            try{
                this@ContactViewModel._contactsList.postValue(getUserContactsFromWeb())

            }
            catch (e: Exception){
                e.printStackTrace()
                this@ContactViewModel._contactsList.postValue(emptyList()) //TODO GET ELEMENTS FROM ROOM
            }
        }
        _selectedContactsList.postValue(emptyList())
    }

    private suspend fun getUserContactsFromWeb(): List<Contact> {
        val userContacts = contactsRepository
            .getUserContacts(userData.user.id.toString(),userData.accessToken)
        return userContacts.data.contacts

    }

    fun deleteContact(contact: Contact): Boolean {
        val list = _contactsList
        if (list.value?.contains(contact) == true) {
            val currentContacts = list.value.orEmpty().toMutableList()
            currentContacts.remove(contact)
            list.value = currentContacts
            deleteContactOnServer(contact.id)
            return true
        }
        return false
    }

    private fun deleteContactOnServer(deleteId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                contactsRepository.deleteContact(
                    userData.user.id.toString(),
                    userData.accessToken,
                    deleteId.toString())
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun addContact(contact: Contact, position: Int) {
        val list = _contactsList
        if (list.value?.contains(contact) == false) {
            val currentContacts = list.value.orEmpty().toMutableList()
            if (currentContacts.size < position)
                currentContacts.add(contact)
            else currentContacts.add(position, contact)
            addContactOnServer(contact.id)
            list.value = currentContacts
        }
    }

    private fun addContactOnServer(addId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                contactsRepository.addContact(
                    userData.user.id.toString(),
                    userData.accessToken,
                    ContactRequest(addId))
            }
            catch (e: Exception){
                e.printStackTrace()
            }
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