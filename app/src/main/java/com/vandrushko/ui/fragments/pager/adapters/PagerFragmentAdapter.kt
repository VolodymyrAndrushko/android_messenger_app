package com.vandrushko.ui.fragments.pager.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vandrushko.ui.fragments.pager.contacts.ContactsFragment
import com.vandrushko.ui.fragments.pager.PagerFragment
import com.vandrushko.ui.fragments.pager.userProfile.UserProfileFragment


class PagerFragmentAdapter(
    pagerFragment: PagerFragment
) : FragmentStateAdapter(pagerFragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                val userProfileFragment = UserProfileFragment()
                userProfileFragment
            }
            else -> ContactsFragment()
        }
    }

}