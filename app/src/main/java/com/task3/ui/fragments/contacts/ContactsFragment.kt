package com.task3.ui.fragments.contacts

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.task3.R
import com.task3.databinding.FragmentContactsBinding
import com.task3.domain.dataclass.Contact
import com.task3.domain.repository.IContactsRecyclerViewAdapter
import com.task3.ui.fragments.Configs
import com.task3.ui.fragments.contacts.adapters.ContactsRecyclerViewAdapter
import com.task3.ui.fragments.contacts.dialogFragments.AddContactFragmentDialog

private const val ADD_CONTACT_FRAGMENT_TAG = "contacts_add_fragment_dialog"

class ContactsFragment : Fragment(), IContactsRecyclerViewAdapter {
    private val navController get() = findNavController()

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = requireNotNull(_binding)
    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)

        setSharedElementsTransition()

        setListeners()
        setRecyclerView()

        return binding.root
    }

    private fun setSharedElementsTransition() {
//        binding.ivProfilePhoto.transitionName = Configs.TRANSITION_NAME_IMAGE+"${contact.id}"
//        binding.fullNameText.transitionName = Configs.TRANSITION_NAME_FULL_NAME+"${contact.id}"
//        binding.tvCareerText.transitionName = Configs.TRANSITION_NAME_CAREER+"${contact.id}"

        val animation = TransitionInflater.from(context).inflateTransition(
            R.transition.photo_transition
//            android.R.transition.move
        )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        startPostponedEnterTransition()
    }

    private fun setListeners() {
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
            showAddContactFragment()
        }
    }

    private fun showAddContactFragment() {
        val dialogFragment = AddContactFragmentDialog(this)
        dialogFragment.show(parentFragmentManager, AddContactFragmentDialog.TAG)
    }

    private fun setRecyclerView() {
        setTouchRecycleItemListener()
        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(context)

        val adapter = ContactsRecyclerViewAdapter(this, this)
        binding.recyclerViewContacts.adapter = adapter

        viewModel = setProvider(this)
        setObserver(viewModel, adapter)
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

    override fun deleteContact(contact: Contact, position: Int) {
        deleteItemWithRestore(contact, position)
    }

    override fun viewDetails(contact: Contact, transitionPairs: Array<Pair<View, String>>) {
//        navigator().showContactProfileScreen(contact)
//        navController.navigate(R.id.action_fragmentContacts_to_fragmentProfile, bundleOf(Configs.ARG_CONTACT to contact))

        val extras = FragmentNavigatorExtras(*transitionPairs)
        val action =
            ContactsFragmentDirections.actionFragmentContactsToFragmentProfile(contact)
        navController.navigate(
            action, extras
        )
    }

    override fun addContact(contact: Contact) {
        viewModel.addContact(
            contact,
            viewModel.contactsList.value?.size ?: 0
        )
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}