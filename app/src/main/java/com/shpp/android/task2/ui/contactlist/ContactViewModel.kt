package com.shpp.android.task2.ui.contactlist


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shpp.android.task2.domain.dataclass.Contact
import com.shpp.android.task2.data.LocalRepositoryData

class ContactViewModel : ViewModel() {

    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>> = _contactsList

    init {
        _contactsList.postValue(LocalRepositoryData().getLocalContactsList())
    }

    fun deleteContact(contact: Contact) {
        val currentContacts = _contactsList.value.orEmpty().toMutableList()
        currentContacts.remove(contact)
        _contactsList.value = currentContacts
    }

    fun addContact(contact: Contact, position: Int = _contactsList.value.orEmpty().size) {
        val currentContacts = _contactsList.value.orEmpty().toMutableList()
        currentContacts.add(position, contact)
        _contactsList.value = currentContacts
    }
}