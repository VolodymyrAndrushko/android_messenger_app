package com.vandrushko.domain.repository

import android.view.View
import com.vandrushko.domain.dataclass.Contact


interface IContactsRecyclerViewAdapter {        // TODO UI interface in domain?
    fun deleteContact(contact: Contact, position: Int)
    fun viewDetails(contact: Contact, transitionPairs: Array<Pair<View, String>>)
    fun showDeleteButton()
    fun hideDeleteButton()
    fun addSelectedItem(contact: Contact)
    fun removeSelectedItem(contact: Contact)
    fun isMultiselectModeActivated(): Boolean
    fun turnOnSelectionMode()
    fun turnOffSelectionMode()
}