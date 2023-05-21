package com.shpp.android.task2.ui.contactslist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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
    private val context: Context, private var listener: IContactsRecyclerViewAdapter
) : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder>() {
    private val contactsList = ArrayList<Contact>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            ContactsRecyclerViewRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindTo(contactsList[position], position)
    }

    inner class ContactsViewHolder(private val binding: ContactsRecyclerViewRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(contact: Contact, position: Int) {
            val localDrawable = R.drawable.ic_profile_default_photo

            with(binding) {
                tvFullName.text = contact.fullName
                tvCareer.text = contact.career

                Glide.with(context).load(contact.image).apply(
                    RequestOptions().centerCrop().transform(CircleCrop()).placeholder(localDrawable)
                        .error(localDrawable).diskCacheStrategy(DiskCacheStrategy.ALL)
                ).into(ivProfilePhoto)
            }
            setListener(contact, position)
        }

        private fun setListener(contact: Contact, position: Int) {
            with(binding) {
                ivDeleteButton.setOnClickListener {
                    listener.onItemClicked(
                        contact, position
                    )
                }
            }
        }
    }

    fun updateList(newList: List<Contact>) {
        val diffResult = DiffUtil.calculateDiff(ContactDiffCallback(contactsList, newList))
        contactsList.clear()
        contactsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}

