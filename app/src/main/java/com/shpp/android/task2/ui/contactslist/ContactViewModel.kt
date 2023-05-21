package com.shpp.android.task2.ui.contactslist


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

    fun deleteContact(contact: Contact): Boolean {
        if (_contactsList.value?.contains(contact) == true) {
            val currentContacts = _contactsList.value.orEmpty().toMutableList()
            currentContacts.remove(contact)
            _contactsList.value = currentContacts
            return true
        }
        return false
    }

    fun addContact(contact: Contact, position: Int) {
        if (_contactsList.value?.contains(contact) == false) {
            val currentContacts = _contactsList.value.orEmpty().toMutableList()
            if (currentContacts.size < position)
                currentContacts.add(contact)
            else currentContacts.add(position, contact)

            _contactsList.value = currentContacts
        }
    }
}
