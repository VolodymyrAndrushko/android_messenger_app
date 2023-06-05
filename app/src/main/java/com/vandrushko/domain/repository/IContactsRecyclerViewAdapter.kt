package com.vandrushko.domain.repository

import android.view.View
import com.vandrushko.domain.dataclass.Contact
import kotlinx.android.parcel.Parcelize


interface IContactsRecyclerViewAdapter{
    fun deleteContact(contact: Contact, position: Int)
    fun viewDetails(contact: Contact, transitionPairs: Array<Pair<View, String>>)
}