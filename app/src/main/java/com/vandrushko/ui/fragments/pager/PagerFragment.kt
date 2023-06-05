package com.vandrushko.ui.fragments.pager

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.vandrushko.R
import com.vandrushko.databinding.FragmentViewPagerTabLayoutBinding
import com.vandrushko.ui.utils.BaseFragment

class PagerFragment : BaseFragment<FragmentViewPagerTabLayoutBinding>(FragmentViewPagerTabLayoutBinding::inflate) {
    private val args: PagerFragmentArgs by navArgs<PagerFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = PagerFragmentAdapter(this, args)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position){
                0->  getString(R.string.my_profile)
                else -> getString(R.string.contacts)
            }
        }.attach()
    }
}