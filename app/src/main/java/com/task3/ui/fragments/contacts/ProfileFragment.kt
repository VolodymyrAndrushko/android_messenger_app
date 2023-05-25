package com.task3.ui.fragments.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.task3.databinding.FragmentDetailViewBinding

class ProfileFragment : Fragment(){
    private lateinit var binding : FragmentDetailViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailViewBinding.inflate(inflater, container, false)

        setProfile()
        setListeners()

        return binding.root
    }

    private fun setProfile() {
        with(binding){
            setProfileText(fullNameText, tvCareerText)
            setProfileImage(ivProfilePhoto)
        }
    }

    private fun setProfileText(fullNameText: AppCompatTextView, tvCareerText: AppCompatTextView) {
        fullNameText.text = requireArguments().getString(ARG_FULL_NAME_VALUE)
        tvCareerText.text = requireArguments().getString(ARG_CAREER_VALUE)
    }

    private fun setProfileImage(view: ImageView) {
        val image = requireArguments().getString(ARG_IMAGE_VALUE)
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
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setMessageButtonOnClickListener() {
        binding.btnMessage.setOnClickListener {
            Toast.makeText(context, binding.fullNameText.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        @JvmStatic
        private val ARG_FULL_NAME_VALUE = "ARG_FULL_NAME_VALUE"

        @JvmStatic
        private val ARG_CAREER_VALUE = "ARG_CAREER_VALUE"

        @JvmStatic
        private val ARG_IMAGE_VALUE = "ARG_IMAGE_VALUE"

        @JvmStatic
        fun newInstance(fullName: String, career: String, image: String?):ProfileFragment{
            val args = Bundle().apply {
                putString(ARG_FULL_NAME_VALUE, fullName)
                putString(ARG_CAREER_VALUE, career)
                putString(ARG_IMAGE_VALUE, image)
            }
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
