package com.vandrushko.ui.utils

import android.app.Activity
import androidx.viewpager2.widget.ViewPager2
import com.vandrushko.R

fun changePageTo(activity: Activity, targetPageIndex: Int) {
    val viewPager = activity.findViewById<ViewPager2>(R.id.viewPager)
    viewPager.currentItem = targetPageIndex
}