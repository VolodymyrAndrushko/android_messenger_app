package com.shpp.android.task2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2.databinding.ActivityContactBinding
import com.shpp.android.task2.data.adapters.ContactsRecyclerViewAdapter
import com.shpp.android.task2.data.adapters.IContactsRecyclerViewAdapter
import com.shpp.android.task2.models.Contact
import com.shpp.android.task2.viewModels.ContactViewModel

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

    private fun setRecyclerView() {
        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(this)
        val adapter = ContactsRecyclerViewAdapter(this, this)
        binding.recyclerViewContacts.isNestedScrollingEnabled = true
        binding.recyclerViewContacts.adapter = adapter

        viewModel = ViewModelProvider(
            this
        )[ContactViewModel::class.java]

        viewModel.contactsList.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })
    }

    private fun setEventListeners() {
        setNavigationUpListeners()
    }

    private fun setNavigationUpListeners() {
        binding.navigationUp.viewTreeObserver.addOnScrollChangedListener {
            checkForDisplayUpNavigationButton()
        }
        binding.navigationUp.setOnClickListener {
            binding.scrollViewLayout.smoothScrollTo(0, 0)
        }
    }

    private fun checkForDisplayUpNavigationButton() {
        val scrollY = binding.scrollViewLayout.scrollY
        val layoutHeight = binding.scrollViewLayout.height
        val contentSize = binding.scrollViewLayout.getChildAt(0).height
        if (
            (scrollY + layoutHeight >= contentSize) &&
            contentSize > layoutHeight

        ) {
            binding.navigationUp.animateVisibility(View.VISIBLE)
        } else if (scrollY == 0) {
            binding.navigationUp.animateVisibility(View.GONE)
        }
    }

    override fun onItemClicked(contact: Contact) {
        viewModel.deleteContact(contact)
        checkForDisplayUpNavigationButton()
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
}
