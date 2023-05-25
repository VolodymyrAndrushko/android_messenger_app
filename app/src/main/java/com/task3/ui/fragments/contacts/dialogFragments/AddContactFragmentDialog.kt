package com.task3.ui.fragments.contacts.dialogFragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.task3.databinding.ContactsAddFragmentDialofBinding

private const val DIALOG_NAME = "Add Contact"
private const val NEGATIVE_BUTTON_TEXT = "Cancel"
private const val POSITIVE_BUTTON_TEXT = "Save"

open class AddContactFragmentDialog : AppCompatDialogFragment() {
    private lateinit var binding: ContactsAddFragmentDialofBinding
    private var positiveButtonClickListener: ((String, String) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = requireActivity().layoutInflater
        binding = ContactsAddFragmentDialofBinding.inflate(inflater, null, false)
        val view: View = binding.root

        builder.setView(view).setTitle(DIALOG_NAME)
            .setPositiveButton(POSITIVE_BUTTON_TEXT) { _, _ ->
                positiveButtonClickListener?.invoke(
                    binding.tiFullName.editText?.text.toString(),
                    binding.tiCareer.editText?.text.toString(),
                )
            }.setNegativeButton(NEGATIVE_BUTTON_TEXT, null)

        return builder.create()
    }

    fun setPositiveButtonClickListener(listener: (String, String) -> Unit) {
        positiveButtonClickListener = listener
    }
}