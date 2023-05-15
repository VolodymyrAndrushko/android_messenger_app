package com.shpp.android.task2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.shpp.android.task2.models.Contact


class ContactsRecyclerViewAdapter(
    val context: Context,
    val listener: IContactsRecyclerViewAdapter
) : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder>() {

    val contactList = ArrayList<Contact>()

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profilePhotoIV: ImageView = itemView.findViewById<ImageView>(R.id.iv_profile_photo)
        val fullNameTV: TextView = itemView.findViewById<TextView>(R.id.tv_full_name)
        val careerTV: TextView = itemView.findViewById<TextView>(R.id.tv_career)
        val deleteButton: ImageView = itemView.findViewById<ImageView>(R.id.iv_delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val viewHolder = ContactsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.contacts_recycler_view_row, parent, false)
        )
        viewHolder.deleteButton.setOnClickListener {
            listener.onItemClicked(contactList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val currentContact = contactList[position]
//        holder.profilePhotoIV.setImageURI(currentContact.image)
        holder.fullNameTV.text = currentContact.fullName
        holder.careerTV.text = currentContact.career
    }

    fun updateList(newList: List<Contact>) {
        contactList.clear()
        contactList.addAll(newList)

        notifyDataSetChanged()
    }
}

interface IContactsRecyclerViewAdapter {
    fun onItemClicked(contact: Contact)
}