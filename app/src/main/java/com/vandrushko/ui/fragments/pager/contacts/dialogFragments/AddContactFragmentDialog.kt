package com.vandrushko.ui.fragments.pager.contacts.dialogFragments

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.vandrushko.R
import com.vandrushko.data.model.ContactRequest
import com.vandrushko.databinding.ContactsAddFragmentDialofBinding
import com.vandrushko.ui.utils.ext.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class AddContactFragmentDialog :
    DialogFragment() {
    private var imageUrl: String? = null

    private var _binding: ContactsAddFragmentDialofBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: AddContactViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ContactsAddFragmentDialofBinding.inflate(layoutInflater)


        return activity?.let {
            val builder = AlertDialog.Builder(it)

            binding.ivProfilePhoto.loadImage()

            setListeners()

            return@let builder
                .setView(binding.root)
                .create()
        } ?: throw IllegalStateException()
    }

    private fun setListeners() {
        setButtonSaveOnClickListener()
        setAddPhotoOnClickListener()
        setNavigationBackListener()
    }


    private fun setButtonSaveOnClickListener() {
        with(binding) {
            btnSave.setOnClickListener {
                viewModel.addContact(
                    "", "", ContactRequest(
                        12
                    )
                )
                dismiss()
            }
        }
    }

    private fun setAddPhotoOnClickListener() {
        binding.addPhoto.setOnClickListener {
            Toast.makeText(context, getString(R.string.add_photo), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setNavigationBackListener() {
        binding.navigationBack.setOnClickListener {
            dismiss()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}