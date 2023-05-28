package com.task3.ui.fragments.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.task3.R
import com.task3.databinding.ContactsRecyclerViewRowBinding
import com.task3.domain.dataclass.Contact
import com.task3.domain.repository.IContactsRecyclerViewAdapter
import com.task3.ui.fragments.contacts.ContactsFragment
import com.task3.ui.fragments.contacts.adapters.diff.ContactDiffCallback

class ContactsRecyclerViewAdapter(
    private val context: ContactsFragment,
    private val listener: IContactsRecyclerViewAdapter,
) : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder>() {
    private val contactsList = ArrayList<Contact>()
    inner class ContactsViewHolder(val binding: ContactsRecyclerViewRowBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ContactsRecyclerViewRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ContactsViewHolder(binding)

    }

    override fun getItemCount(): Int = contactsList.size


    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val localDrawable = R.drawable.ic_profile_default_photo
        with(holder.binding) {
            with(contactsList[position]){
                tvFullName.text = fullName
                tvCareer.text = career
                Glide
                    .with(context)
                    .load(image)
                    .apply(
                        RequestOptions().centerCrop().transform(CircleCrop()).placeholder(localDrawable)
                            .error(localDrawable).diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .into(ivProfilePhoto)

                ivDeleteButton.setOnClickListener {
                    listener.deleteContact(
                        this, position
                    )
                }
                contact.setOnClickListener {
                    listener.viewDetails(
                        this
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