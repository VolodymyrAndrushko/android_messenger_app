package com.vandrushko.ui.fragments.userProfile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.vandrushko.R
import com.vandrushko.databinding.FragmentUserProfileBinding
import com.vandrushko.ui.fragments.Configs
import com.vandrushko.ui.fragments.pager.PagerFragmentArgs
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.Parser
import com.vandrushko.ui.utils.ext.loadImage

class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {
    private val args: PagerFragmentArgs by navArgs<PagerFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profilePhoto.loadImage()
        setProfileFullName()

    }

    private fun setProfileFullName() {
        val email: String? = args.email
        val fullName: String? = Parser().parseEmail(email)

        if(fullName!=null) binding.fullNameText.text = fullName
    }
}