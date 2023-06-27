package com.vandrushko.ui.fragments.contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
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
import com.vandrushko.ui.utils.ext.hide
import com.vandrushko.ui.utils.ext.show

class ContactsRecyclerViewAdapter(
    private val context: ContactsFragment,
    private val listener: IContactsRecyclerViewAdapter,
    private val selectedContactsList: LiveData<List<Contact>>,
) : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder>() {
    private val contactsList = ArrayList<Contact>()

    private var selectedItemIndex = -1

    private var transitionPairs = emptyArray<Pair<View, String>>()

    inner class ContactsViewHolder(val binding: ContactsRecyclerViewRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateList(newList: List<Contact>) {
        val diffResult = DiffUtil.calculateDiff(ContactDiffCallback(contactsList, newList))
        contactsList.clear()
        contactsList.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }

    private fun setListeners(
        binding: ContactsRecyclerViewRowBinding,
        contact: Contact,
        position: Int
    ) {
        setTextViews(binding, contact)  // TODO it's not a listener

        setImage(binding, contact)  // TODO it's not a listener

        setDeleteButtonOnClickListener(binding, position, contact)

        setCardViewOnClickListener(binding, contact, position)
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

        Glide       // TODO why u r not using your own ImageView.loadImage ext function?
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
        contact: Contact,
        position: Int
    ) {
        with(binding) {
            cardView.setOnClickListener {
                if (!listener.isMultiselectModeActivated()) {
                    viewDetailView(this, contact)
                } else if (isChecked(checkboxContacts)) {
                    unselectItem(this, contact)
                    hideIfNoItemSelected()
                } else {
                    selectItem(this, contact)
                }

            }
            cardView.setOnLongClickListener {
                if (!listener.isMultiselectModeActivated()) {
                    activateMultiSelectMode(position)
                }
                true
            }
        }

    }

    private fun hideIfNoItemSelected() {
        if (selectedContactsList.value?.isEmpty() == true) {
            listener.turnOffSelectionMode()
            listener.hideDeleteButton()
            notifyDataSetChanged()
        }
    }

    private fun selectItem(binding: ContactsRecyclerViewRowBinding, contact: Contact) {
        with(binding) {
            checkboxContacts.show()
            checkboxContacts.isChecked = true
            cardConstraintLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context.requireContext(),
                    R.color.contacts_selected_color
                )
            )
        }
        listener.addSelectedItem(contact)
    }

    private fun unselectItem(binding: ContactsRecyclerViewRowBinding, contact: Contact) {
        with(binding) {
            checkboxContacts.isChecked = false
            cardConstraintLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context.requireContext(),
                    R.color.background_color
                )
            )
        }
        listener.removeSelectedItem(contact)
    }

    private fun isChecked(checkboxContacts: CheckBox): Boolean = checkboxContacts.isChecked // TODO why?????

    private fun viewDetailView(binding: ContactsRecyclerViewRowBinding, contact: Contact) {
        with(binding) {
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

    private fun activateMultiSelectMode(position: Int) {
        listener.turnOnSelectionMode()
        listener.showDeleteButton()     // TODO encapsulate
        selectedItemIndex = position
        notifyDataSetChanged()
    }

    private fun setTransitionName(view: View, name: String): Pair<View, String> {
        view.transitionName = name
        return view to name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ContactsRecyclerViewRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return (ContactsViewHolder(binding))
    }

    override fun getItemCount(): Int = contactsList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {  // TODO binding logic should be inside ViewHolder
        val contact = contactsList[position]
        val binding = holder.binding

        if (listener.isMultiselectModeActivated()) {
            binding.checkboxContacts.show()
            if (selectedContactsList.value.orEmpty()
                    .contains(contact) || selectedItemIndex == position
            ) {
                selectItem(binding, contact)
            }
        } else {
            binding.checkboxContacts.hide()
        }
        setListeners(binding, contact, position)
    }
}