package com.vandrushko.ui.fragments.pager.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vandrushko.ui.fragments.contacts.ContactsFragment
import com.vandrushko.ui.fragments.pager.PagerFragment
import com.vandrushko.ui.fragments.pager.PagerFragmentArgs
import com.vandrushko.ui.fragments.userProfile.UserProfileFragment


class PagerFragmentAdapter(
    pagerFragment: PagerFragment,
    private val args: PagerFragmentArgs
) : FragmentStateAdapter(pagerFragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                val userProfileFragment = UserProfileFragment()
                userProfileFragment.arguments = args.toBundle()
                userProfileFragment
            }
            else -> ContactsFragment()
        }
    }

}