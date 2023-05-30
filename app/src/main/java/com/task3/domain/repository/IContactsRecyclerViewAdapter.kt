package com.task3.domain.repository

import android.view.View
import com.task3.domain.dataclass.Contact

interface IContactsRecyclerViewAdapter {
    fun deleteContact(contact: Contact, position: Int)
    fun viewDetails(contact: Contact, view: Array<Pair<View, String>>)

    fun addContact(contact:Contact)
}