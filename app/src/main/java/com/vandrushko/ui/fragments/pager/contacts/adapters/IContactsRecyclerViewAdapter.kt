package com.vandrushko.ui.fragments.pager.contacts.adapters

import android.view.View
import com.vandrushko.data.model.Contact


interface IContactsRecyclerViewAdapter {
    fun deleteContact(contact: Contact, position: Int)
    fun viewDetails(contact: Contact, transitionPairs: Array<Pair<View, String>>)
    fun addSelectedItem(contact: Contact, position: Int)
    fun removeSelectedItem(contact: Contact, position: Int)
    fun isMultiselectModeActivated(): Boolean
    fun turnOnSelectionMode()
    fun turnOffSelectionMode()
}