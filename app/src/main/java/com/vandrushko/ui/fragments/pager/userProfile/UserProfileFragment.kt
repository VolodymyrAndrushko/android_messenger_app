package com.vandrushko.ui.fragments.pager.userProfile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.vandrushko.R
import com.vandrushko.databinding.FragmentUserProfileBinding
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.Parser
import com.vandrushko.ui.utils.ext.loadImage
import com.vandrushko.ui.utils.ext.setTransparentIfNotPresentInContact
import dagger.hilt.android.AndroidEntryPoint

private const val CONTACTS_PAGE_INDEX = 1

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {

    private val viewModel: UserProfileViewModel by viewModels()
    private fun setListeners() {
        setGoToContactsOnClickListener()
    }

    private fun setGoToContactsOnClickListener() {
        binding.viewContactsButton.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
            viewPager.currentItem = CONTACTS_PAGE_INDEX

        }
    }

    private fun setProfile() {
        val contact = viewModel.getUserData()
        with(contact) {
            with(binding){
                val fullName: String? = name ?: Parser.parseEmail(email)
                fullName?.let {fullNameText.text = it}
                career?.let {careerText.text = career}
                address?.let { homeAddressText.text = it }

                icon1.setTransparentIfNotPresentInContact(facebook)
                icon2.setTransparentIfNotPresentInContact(linkedin)
                icon3.setTransparentIfNotPresentInContact(instagram)

                image?.let { profilePhoto.loadImage(it)}
            }

        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profilePhoto.loadImage()
        setProfile()
        setListeners()
    }
}