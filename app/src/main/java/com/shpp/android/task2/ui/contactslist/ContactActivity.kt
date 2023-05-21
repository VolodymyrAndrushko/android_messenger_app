package com.shpp.android.task2.ui.contactslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.databinding.ActivityContactBinding
import com.google.android.material.snackbar.Snackbar
import com.shpp.android.task2.ui.contactslist.adapters.ContactsRecyclerViewAdapter
import com.shpp.android.task2.domain.repository.IContactsRecyclerViewAdapter
import com.shpp.android.task2.ui.contactslist.fragments.ContactAddFragmentDialog
import com.shpp.android.task2.domain.dataclass.Contact

private const val ADD_CONTACT_FRAGMENT_TAG = "contacts_add_fragment_dialog"

class ContactActivity : AppCompatActivity(), IContactsRecyclerViewAdapter {
    private lateinit var binding: ActivityContactBinding
    private lateinit var viewModel: ContactViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEventListeners()
        setRecyclerView()
    }

    private fun setEventListeners() {
        setNavigationUpListeners()
        setAddContactButtonListener()
    }

    private fun setNavigationUpListeners() {
        binding.navigationUp.viewTreeObserver.addOnScrollChangedListener {
            checkForDisplayUpNavigationButton()
        }
        binding.navigationUp.setOnClickListener {
            binding.scrollViewLayout.smoothScrollTo(0, 0)
        }
    }

    private fun setAddContactButtonListener() {
        binding.addContacts.setOnClickListener {
            val dialogFragment = ContactAddFragmentDialog()
            dialogFragment.setPositiveButtonClickListener(object : ContactAddFragmentDialog(),
                    (String, String) -> Unit {
                override fun invoke(fullName: String, career: String) {
                    viewModel.addContact(
                        Contact(fullName, career),
                        viewModel.contactsList.value?.size ?: 0
                    )
                }
            })
            dialogFragment.show(supportFragmentManager, ADD_CONTACT_FRAGMENT_TAG)
        }
    }

    private fun setRecyclerView() {
        setTouchRecycleItemListener()
        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(this)

        val adapter = ContactsRecyclerViewAdapter(this, this)
        binding.recyclerViewContacts.adapter = adapter

        viewModel = setProvider(this)
        setObserver(viewModel, adapter)

    }

    private fun setTouchRecycleItemListener() {
        val itemTouchCallback = setTouchCallBackListener()
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.recyclerViewContacts)
    }

    private fun setProvider(context: ContactActivity): ContactViewModel {
        return ViewModelProvider(
            this
        )[ContactViewModel::class.java]
    }

    private fun setObserver(viewModel: ContactViewModel, adapter: ContactsRecyclerViewAdapter) {
        viewModel.contactsList.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })
    }

    private fun setTouchCallBackListener(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val contact = viewModel.contactsList.value.orEmpty()[position]
                deleteItemWithRestore(contact, position)
            }
        }
    }


    private fun checkForDisplayUpNavigationButton() {
        val scrollY = binding.scrollViewLayout.scrollY
        val layoutHeight = binding.scrollViewLayout.height
        val contentSize = binding.scrollViewLayout.getChildAt(0).height
        if ((scrollY + layoutHeight >= contentSize) && contentSize > layoutHeight

        ) {
            binding.navigationUp.animateVisibility(View.VISIBLE)
        } else if (scrollY == 0) {
            binding.navigationUp.animateVisibility(View.GONE)
        }
    }

    override fun onItemClicked(contact: Contact, position: Int) {
        deleteItemWithRestore(contact, position)
    }

    private fun View.animateVisibility(visibility: Int) {
        val animator: ViewPropertyAnimator = when (visibility) {
            View.VISIBLE -> animate().alpha(1f).setDuration(300)
            View.GONE -> animate().alpha(0f).setDuration(300)
            else -> return
        }
        animator.withStartAction {
            this.visibility = View.VISIBLE
        }.withEndAction {
            this.visibility = visibility
        }.start()
    }

    private fun deleteItemWithRestore(contact: Contact, position: Int) {
        if (viewModel.deleteContact(contact)) {
            Snackbar.make(
                binding.recyclerViewContacts,
                "Contact ${contact.fullName} deleted",
                Snackbar.LENGTH_LONG
            ).setAction("Restore") {
                viewModel.addContact(contact, position)
            }.show()
        }
        checkForDisplayUpNavigationButton()
    }
}
