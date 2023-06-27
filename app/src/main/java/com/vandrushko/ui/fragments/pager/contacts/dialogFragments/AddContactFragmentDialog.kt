package com.vandrushko.ui.fragments.pager.contacts.dialogFragments

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.vandrushko.R
import com.vandrushko.databinding.ContactsAddFragmentDialofBinding
import com.vandrushko.data.model.Contact
import com.vandrushko.ui.fragments.contacts.dialogFragments.AddContactFragmentDialogArgs
import com.vandrushko.ui.fragments.pager.contacts.ContactViewModel
import com.vandrushko.ui.utils.ext.loadImage

open class AddContactFragmentDialog :
    DialogFragment() {

    private val args: AddContactFragmentDialogArgs by navArgs<AddContactFragmentDialogArgs>()

    private lateinit var viewModel: ContactViewModel

    private var imageUrl: String? = null

    private var _binding: ContactsAddFragmentDialofBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ContactsAddFragmentDialofBinding.inflate(layoutInflater)

        viewModel = args.viewModel

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
                    Contact().apply {
                        name = tiFullName.editText?.text.toString()
                        career = tiCareer.editText?.text.toString()
                        email = tiEmail.editText?.text.toString()
                        image = imageUrl
                        phone = tiPhone.editText?.text.toString()
                        address = tiAddress.editText?.text.toString()
                        birthday = tiBirth.editText?.text.toString()
                    }, viewModel.contactsList.value?.size ?: 0
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

    companion object {
        @JvmStatic
        val TAG: String = AddContactFragmentDialog::class.java.name
    }
}