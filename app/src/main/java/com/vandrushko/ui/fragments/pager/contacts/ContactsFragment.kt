package com.vandrushko.ui.fragments.pager.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.vandrushko.R
import com.vandrushko.data.model.Contact
import com.vandrushko.databinding.FragmentContactsBinding
import com.vandrushko.ui.fragments.pager.PagerFragmentDirections
import com.vandrushko.ui.fragments.pager.contacts.adapters.ContactsRecyclerViewAdapter
import com.vandrushko.ui.fragments.pager.contacts.adapters.IContactsRecyclerViewAdapter
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.ext.animateVisibility
import com.vandrushko.ui.utils.ext.hide
import com.vandrushko.ui.utils.ext.show
import dagger.hilt.android.AndroidEntryPoint
import java.util.List.copyOf


private const val MY_PROFILE_PAGE_INDEX = 0

@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    private val viewModel: ContactViewModel by viewModels()

    private val itemTouchHelper = ItemTouchHelper(getTouchCallBackListener())

    private lateinit var adapter: ContactsRecyclerViewAdapter

    private val listener = object : IContactsRecyclerViewAdapter {
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

        override fun addSelectedItem(contact: Contact, position: Int) {
            viewModel.addSelectedContact(contact to position)
        }

        override fun removeSelectedItem(contact: Contact, position: Int) {
            viewModel.deleteSelectedContact(contact to position)
        }

        override fun turnOnSelectionMode() {
            viewModel.turnOnSelectionMode()
            itemTouchHelper.attachToRecyclerView(null)
            binding.buttonDeleteSelected.show()

        }

        override fun turnOffSelectionMode() {
            viewModel.turnOffSelectionMode()
            itemTouchHelper.attachToRecyclerView(binding.recyclerViewContacts)
            binding.buttonDeleteSelected.hide()
        }

        override fun isMultiselectModeActivated(): Boolean = viewModel.isMultiselectModeActivated()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        adapter = ContactsRecyclerViewAdapter(this, listener, viewModel.selectedContactsList)

        setRecyclerView()

        viewModel.contactsList.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        if (viewModel.isMultiselectModeActivated()) {
            binding.buttonDeleteSelected.show()
        } else {
            itemTouchHelper.attachToRecyclerView(binding.recyclerViewContacts)
        }
    }

    override fun onResume() {
        super.onResume()
        startPostponedEnterTransition()

    }

    private fun setListeners() {
        setNavigationUpListeners()
        setAddContactButtonListener()
        setBackArrowOnClickListener()
        setDeleteSelectedButtonOnClickListener()
    }

    private fun setBackArrowOnClickListener() {
        binding.navigationBack.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
            viewPager.currentItem = MY_PROFILE_PAGE_INDEX
        }
    }

    private fun setDeleteSelectedButtonOnClickListener() {
        binding.buttonDeleteSelected.setOnClickListener {
            deleteItemsWithRestore()
        }

    }

    private fun deleteItemsWithRestore() {
        val selectedItems = viewModel.selectedContactsList.value?.let { copyOf(it) }

        if (viewModel.selectedContactsList.value?.isNotEmpty() == true) {
            viewModel.deleteSelectedContacts()
            binding.buttonDeleteSelected.hide()

            setRecyclerView()
            adapter.notifyDataSetChanged()
        }

        Snackbar.make(
            binding.recyclerViewContacts,
            getString(R.string.deletedContacts),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.restore)) {
            viewModel.addContacts(selectedItems)
            adapter.notifyDataSetChanged()
        }.show()
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
            PagerFragmentDirections.actionPagerFragmentToAddContactFragmentDialog()
        navController.navigate(action)
    }

    private fun setRecyclerView() {
        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(context)

        binding.recyclerViewContacts.adapter = adapter
    }

    private fun getTouchCallBackListener(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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

    private fun deleteItemWithRestore(contact: Contact, position: Int) {
        if (viewModel.deleteContact(contact)) {
            Snackbar.make(
                binding.recyclerViewContacts,
                getString(R.string.deletedContact, contact.name),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.restore)) {
                viewModel.addContact(contact, position)
            }.show()
        }
        checkForDisplayUpNavigationButton()
    }
}