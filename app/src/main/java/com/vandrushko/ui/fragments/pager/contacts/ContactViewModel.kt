package com.vandrushko.ui.fragments.pager.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vandrushko.data.LocalRepositoryData
import com.vandrushko.data.model.Contact
import java.io.Serializable

class ContactViewModel : ViewModel(), Serializable {

    private val _contactsList = MutableLiveData<List<Contact>>()
    private val _selectedContactsList = MutableLiveData<List<Contact>>()
    private var isMultiselectModeActivated = MutableLiveData(false)

    val contactsList: LiveData<List<Contact>> = _contactsList
    val selectedContactsList: LiveData<List<Contact>> = _selectedContactsList

    init {
        _contactsList.postValue(LocalRepositoryData().getLocalContactsList())
        _selectedContactsList.postValue(emptyList())
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

    fun deleteSelectedContact(contact: Contact): Boolean {
        val list = _selectedContactsList
        if (list.value?.contains(contact) == true) {
            val currentContacts = list.value.orEmpty().toMutableList()
            currentContacts.remove(contact)
            list.value = currentContacts
            return true
        }
        return false
    }

    fun addSelectedContact(contact: Contact) {
        val list = _selectedContactsList
        if (list.value?.contains(contact) == false) {
            val currentContacts = list.value.orEmpty().toMutableList()
            currentContacts.add(contact)
            list.value = currentContacts
        }
    }

    fun deleteSelectedContacts() {
        for (contact in _selectedContactsList.value.orEmpty()) {
            deleteSelectedContact(contact)
            deleteContact(contact)
            _selectedContactsList.value = emptyList()
        }
    }

    fun turnOnSelectionMode() {
        isMultiselectModeActivated.value = true
    }

    fun turnOffSelectionMode() {
        isMultiselectModeActivated.value = false
    }

    fun isMultiselectModeActivated(): Boolean = isMultiselectModeActivated.value ?: false
}