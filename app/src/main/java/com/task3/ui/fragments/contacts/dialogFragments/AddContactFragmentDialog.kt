package com.task3.ui.fragments.contacts.dialogFragments

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.task3.R
import com.task3.databinding.ContactsAddFragmentDialofBinding
import com.task3.domain.dataclass.Contact
import com.task3.domain.repository.IContactsRecyclerViewAdapter

//private const val DIALOG_NAME = "Add Contact"
//private const val NEGATIVE_BUTTON_TEXT = "Cancel"
//private const val POSITIVE_BUTTON_TEXT = "Save"

// TODO Property 'positiveButtonClickListener' could be private
open class AddContactFragmentDialog(var positiveButtonClickListener: IContactsRecyclerViewAdapter) :
    DialogFragment() {

    private var imageUrl: String? = null

    private var _binding: ContactsAddFragmentDialofBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ContactsAddFragmentDialofBinding.inflate(layoutInflater)

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            with(binding) {
                setImage(ivProfilePhoto)

                addPhoto.setOnClickListener {
                    Toast.makeText(context, "Add photo", Toast.LENGTH_SHORT).show()
                }

                btnSave.setOnClickListener {
                    positiveButtonClickListener.addContact(
                        Contact(
                            tiFullName.editText?.text.toString(),
                            tiCareer.editText?.text.toString(),
                            imageUrl
                            )
                    )
                    dismiss()
                }

                navigationBack.setOnClickListener {
                    dismiss()
                }
            }

            builder
                .setView(binding.root)
                .create()
        } ?: throw IllegalStateException()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setImage(view: ImageView, image: String? = null) {
        Glide.with(this)
            .load(image)
            .centerCrop()
            .circleCrop()
            .error(R.drawable.ic_profile_default_photo)
            .into(view)
    }

    companion object {
        @JvmStatic
        val TAG: String = AddContactFragmentDialog::class.java.name

        @JvmStatic
        val REQUEST_KEY = "$TAG:RequestKey"

        @JvmStatic
        val RESPONSE_KEY = "$TAG:ResponseKey"
    }
}