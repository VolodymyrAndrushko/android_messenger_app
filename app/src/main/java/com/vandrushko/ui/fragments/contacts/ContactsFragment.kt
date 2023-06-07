package com.vandrushko.ui.fragments.contacts

import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vandrushko.R
import com.vandrushko.databinding.FragmentContactsBinding
import com.vandrushko.domain.dataclass.Contact
import com.vandrushko.domain.repository.IContactsRecyclerViewAdapter
import com.vandrushko.ui.fragments.contacts.adapters.ContactsRecyclerViewAdapter
import com.vandrushko.ui.fragments.pager.PagerFragmentDirections
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.ext.changePageTo
import java.io.Serializable


private const val MY_PROFILE_PAGE_INDEX = 0

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    private lateinit var viewModel: ContactViewModel

    private val listener = object : IContactsRecyclerViewAdapter, Serializable {
        override fun deleteContact(contact: Contact, position: Int) {
            deleteItemWithRestore(contact, position)
        }

        override fun viewDetails(contact: Contact, transitionPairs: Array<Pair<View, String>>) {
            val extras = FragmentNavigatorExtras(*transitionPairs)
            val action =
                PagerFragmentDirections.actionPagerFragmentToProfileFragment2(contact)
            navController.navigate(
                action, extras
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        postponeEnterTransition()
        startPostponedEnterTransition()

    }

    private fun setListeners() {
        setNavigationUpListeners()
        setAddContactButtonListener()
        setBackArrowOnClickListener()
    }

    private fun setBackArrowOnClickListener() {
        binding.navigationBack.setOnClickListener {
//            changePageTo(MY_PROFILE_PAGE_INDEX)
            changePageTo(requireActivity(), MY_PROFILE_PAGE_INDEX)
        }
    }


    private fun setNavigationUpListeners() {
        binding.root.setOnScrollChangeListener { _, _, _, _, _ ->
            checkForDisplayUpNavigationButton()
        }
        binding.navigationUp.setOnClickListener {
            binding.scrollViewLayout.smoothScrollTo(0, 0)
        }
    }

    private fun setAddContactButtonListener() {
        binding.addContacts.setOnClickListener {
            showAddContactFragment()
        }
    }

    private fun showAddContactFragment() {
        val action =
            PagerFragmentDirections.actionPagerFragmentToAddContactFragmentDialog(viewModel)
        navController.navigate(action)
    }

    private fun setRecyclerView() {
        setTouchRecycleItemListener()
        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(context)

        val adapter = ContactsRecyclerViewAdapter(this, listener)
        binding.recyclerViewContacts.adapter = adapter

        viewModel = setProvider(this)
        setObserver(viewModel, adapter)

        startPostponedEnterTransition()
    }

    private fun setTouchRecycleItemListener() {
        val itemTouchCallback = setTouchCallBackListener()
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.recyclerViewContacts)
    }

    private fun setProvider(context: ContactsFragment): ContactViewModel {
        return ViewModelProvider(
            context
        )[ContactViewModel::class.java]
    }

    private fun setObserver(viewModel: ContactViewModel, adapter: ContactsRecyclerViewAdapter) {
        viewModel.contactsList.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
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
                getString(R.string.deletedContact, contact.fullName),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.restore)) {
                viewModel.addContact(contact, position)
            }.show()
        }
        checkForDisplayUpNavigationButton()
    }
}