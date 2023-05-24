package com.shpp.android.task2.ui.contactslist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.task2.R
import com.example.task2.databinding.ContactsRecyclerViewRowBinding
import com.shpp.android.task2.domain.dataclass.Contact
import com.shpp.android.task2.domain.repository.IContactsRecyclerViewAdapter
import com.shpp.android.task2.ui.contactslist.adapters.diff.ContactDiffCallback


class ContactsRecyclerViewAdapter(
    private val context: Context, private val listener: IContactsRecyclerViewAdapter
) : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder>() {
    private val contactsList = ArrayList<Contact>()

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profilePhotoIV: ImageView = itemView.findViewById(R.id.iv_profile_photo)
        val fullNameTV: TextView = itemView.findViewById(R.id.tv_full_name)
        val careerTV: TextView = itemView.findViewById(R.id.tv_career)
        val deleteButton: ImageView = itemView.findViewById(R.id.iv_delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val viewHolder = ContactsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.contacts_recycler_view_row, parent, false)
        )
        viewHolder.deleteButton.setOnClickListener {
            listener.onItemClicked(
                contactsList[viewHolder.adapterPosition], viewHolder.adapterPosition
            )
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {


        val currentContact = contactsList[position]
        val localDrawable = R.drawable.ic_profile_default_photo
        holder.fullNameTV.text = currentContact.fullName
        holder.careerTV.text = currentContact.career
        Glide
            .with(context)
            .load(currentContact.image)
            .apply(
                RequestOptions().centerCrop().transform(CircleCrop()).placeholder(localDrawable)
                    .error(localDrawable).diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(holder.profilePhotoIV)
    }

    fun updateList(newList: List<Contact>) {
        val diffResult = DiffUtil.calculateDiff(ContactDiffCallback(contactsList, newList))
        contactsList.clear()
        contactsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}