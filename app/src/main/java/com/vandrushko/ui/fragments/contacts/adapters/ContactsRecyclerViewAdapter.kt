package com.vandrushko.ui.fragments.contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.vandrushko.R
import com.vandrushko.databinding.ContactsRecyclerViewRowBinding
import com.vandrushko.domain.dataclass.Contact
import com.vandrushko.domain.repository.IContactsRecyclerViewAdapter
import com.vandrushko.ui.fragments.Configs
import com.vandrushko.ui.fragments.contacts.ContactsFragment
import com.vandrushko.ui.fragments.contacts.adapters.diff.ContactDiffCallback

class ContactsRecyclerViewAdapter(
    private val context: ContactsFragment,
    private val listener: IContactsRecyclerViewAdapter,
) : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder>() {
    private val contactsList = ArrayList<Contact>()

    var transitionPairs = emptyArray<Pair<View, String>>()

    inner class ContactsViewHolder(val binding: ContactsRecyclerViewRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ContactsRecyclerViewRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return (ContactsViewHolder(binding))
    }

    override fun getItemCount(): Int = contactsList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contactsList[position]
        val binding = holder.binding

        setListeners(binding, contact, position)
    }

    private fun setListeners(
        binding: ContactsRecyclerViewRowBinding,
        contact: Contact,
        position: Int
    ) {
        setTextViews(binding, contact)

        setImage(binding, contact)

        setDeleteButtonOnClickListener(binding, position, contact)

        setCardViewOnClickListener(binding, contact)
    }

    private fun setTextViews(binding: ContactsRecyclerViewRowBinding, contact: Contact) {
        with(binding) {
            with(contact) {
                tvFullName.text = fullName
                tvCareer.text = career
            }
        }
    }

    private fun setImage(binding: ContactsRecyclerViewRowBinding, contact: Contact) {
        val localDrawable = R.drawable.ic_profile_default_photo

        Glide
            .with(context)
            .load(contact.image)
            .apply(
                RequestOptions().centerCrop().transform(CircleCrop())
                    .placeholder(localDrawable)
                    .error(localDrawable).diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(binding.ivProfilePhoto)
    }

    private fun setDeleteButtonOnClickListener(
        binding: ContactsRecyclerViewRowBinding,
        position: Int,
        contact: Contact
    ) {
        binding.ivDeleteButton.setOnClickListener {
            listener.deleteContact(
                contact, position
            )
        }
    }

    private fun setCardViewOnClickListener(
        binding: ContactsRecyclerViewRowBinding,
        contact: Contact
    ) {
        with(binding) {
            cardView.setOnClickListener {
                transitionPairs = arrayOf(
                    setTransitionName(
                        ivProfilePhoto,
                        Configs.TRANSITION_NAME_IMAGE + contact.id
                    ),
                    setTransitionName(
                        tvFullName,
                        Configs.TRANSITION_NAME_FULL_NAME + contact.id
                    ), setTransitionName(
                        tvCareer,
                        Configs.TRANSITION_NAME_CAREER + contact.id
                    )
                )

                listener.viewDetails(
                    contact, transitionPairs
                )
            }
        }

    }

    fun updateList(newList: List<Contact>) {
        val diffResult = DiffUtil.calculateDiff(ContactDiffCallback(contactsList, newList))
        contactsList.clear()
        contactsList.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }

    private fun setTransitionName(view: View, name: String): Pair<View, String> {
        view.transitionName = name
        return view to name
    }
}