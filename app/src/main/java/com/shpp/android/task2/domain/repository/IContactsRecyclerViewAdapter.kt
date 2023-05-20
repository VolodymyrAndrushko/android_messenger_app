package com.shpp.android.task2.domain.repository

import com.shpp.android.task2.domain.dataclass.Contact

interface IContactsRecyclerViewAdapter {
    fun onItemClicked(contact: Contact, position: Int)
}