package com.task3.domain.repository

import com.task3.domain.dataclass.Contact

interface IContactsRecyclerViewAdapter {
    fun deleteContact(contact: Contact, position: Int)
    fun viewDetails(contact: Contact, position: Int)
}