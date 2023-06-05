package com.vandrushko.ui.fragments.profile

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vandrushko.R
import com.vandrushko.databinding.FragmentDetailViewBinding
import com.vandrushko.domain.dataclass.Contact
import com.vandrushko.ui.fragments.Configs
import com.vandrushko.ui.utils.BaseFragment

class ProfileFragment : BaseFragment<FragmentDetailViewBinding>(FragmentDetailViewBinding::inflate) {
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
            .error(R.drawable.ic_profile_default_photo)
            .into(view)
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
