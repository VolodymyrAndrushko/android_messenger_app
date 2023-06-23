package com.vandrushko.ui.fragments.profile

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vandrushko.databinding.FragmentDetailViewBinding
import com.vandrushko.data.model.Contact
import com.vandrushko.ui.fragments.Configs
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.ext.loadImage
import com.vandrushko.ui.utils.ext.setTransparentIfNotPresentInContact

class ProfileFragment :
    BaseFragment<FragmentDetailViewBinding>(FragmentDetailViewBinding::inflate) {
    private lateinit var contact: Contact
    private val args: ProfileFragmentArgs by navArgs<ProfileFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contact = args.contact

        setSharedElementsTransition()
        setProfile()
        setListeners()
    }

    private fun setSharedElementsTransition() {
        binding.ivProfilePhoto.transitionName = Configs.TRANSITION_NAME_IMAGE + "${contact.id}"
        binding.fullNameText.transitionName = Configs.TRANSITION_NAME_FULL_NAME + "${contact.id}"
        binding.tvCareerText.transitionName = Configs.TRANSITION_NAME_CAREER + "${contact.id}"

        val animation = TransitionInflater.from(context).inflateTransition(
            android.R.transition.move
        )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        postponeEnterTransition()
        startPostponedEnterTransition()
    }

    private fun setProfile() {
        setProfileText()
        binding.ivProfilePhoto.loadImage(contact.image)
        setSocialIcons()
    }

    private fun setProfileText() {
        with(binding) {
            fullNameText.text = contact.name
            tvCareerText.text = contact.career
            tvHomeAddressText.text = contact.address
        }
    }

    private fun setSocialIcons() {
        with(binding) {
            with(contact) {
                ivLinkedinIcon.setTransparentIfNotPresentInContact(linkedin)
                ivInstagramIcon.setTransparentIfNotPresentInContact(instagram)
                ivFacebookIcon.setTransparentIfNotPresentInContact(facebook)
            }
        }
    }

    private fun setListeners() {
        setMessageButtonOnClickListener()
        setNavigationBackListener()
    }

    private fun setNavigationBackListener() {
        binding.navigationBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setMessageButtonOnClickListener() {
        binding.btnMessage.setOnClickListener {
            Toast.makeText(context, binding.fullNameText.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
