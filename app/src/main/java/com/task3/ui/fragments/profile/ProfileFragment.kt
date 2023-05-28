package com.task3.ui.fragments.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.task3.databinding.FragmentDetailViewBinding
import com.task3.domain.dataclass.Contact
import com.task3.ui.fragments.Configs
import java.lang.IllegalArgumentException

class ProfileFragment : Fragment() {
    private var _binding: FragmentDetailViewBinding? = null

    private lateinit var contact: Contact
    private val binding get() = requireNotNull(_binding)
    private val args: ProfileFragmentArgs by navArgs<ProfileFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailViewBinding.inflate(inflater, container, false)

//        contact = getContact() ?: throw IllegalArgumentException(NO_CONTACT_FOUND_EXCEPTION)
        contact = args.contact

        setProfile()
        setListeners()

        return binding.root
    }


    private fun getContact(): Contact? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
            Configs.ARG_CONTACT,
            Contact::class.java
        )
        else requireArguments().getParcelable(Configs.ARG_CONTACT)
    }

    private fun setProfile() {
        with(binding) {
            setProfileText(fullNameText, tvCareerText)
            setProfileImage(ivProfilePhoto)
        }
    }

    private fun setProfileText(fullNameText: AppCompatTextView, tvCareerText: AppCompatTextView) {
        fullNameText.text = contact.fullName
        tvCareerText.text = contact.career

    }

    private fun setProfileImage(view: ImageView) {
        val image = contact.image
        Glide.with(this@ProfileFragment)
            .load(image)
            .centerCrop()
            .circleCrop()
            .into(view)
    }

    private fun setListeners() {
        setMessageButtonOnClickListener()
        setNavigationBackListener()
    }

    private fun setNavigationBackListener() {
        binding.navigationBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setMessageButtonOnClickListener() {
        binding.btnMessage.setOnClickListener {
            Toast.makeText(context, binding.fullNameText.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        @JvmStatic
        private val NO_CONTACT_FOUND_EXCEPTION = "No contact found."

        @JvmStatic
        fun newInstance(contact: Contact): ProfileFragment {
//            val args = Bundle()
//            args.putParcelable(Configs.ARG_CONTACT, contact)
//
//            val fragment = ProfileFragment()
//            fragment.arguments = args
//            return fragment
            return ProfileFragment()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
