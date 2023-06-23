package com.vandrushko.ui.fragments.contacts.adapters

import android.view.View
import com.vandrushko.data.model.Contact


interface IContactsRecyclerViewAdapter {
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