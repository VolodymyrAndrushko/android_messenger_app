package com.task3.domain.repository

import com.task3.domain.dataclass.Contact

interface IContactsRecyclerViewAdapter {
    fun onItemClicked(contact: Contact, position: Int)
}