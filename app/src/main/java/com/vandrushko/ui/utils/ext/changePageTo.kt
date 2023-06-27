package com.vandrushko.ui.utils.ext

import android.app.Activity
import androidx.viewpager2.widget.ViewPager2
import com.vandrushko.R

fun changePageTo(activity: Activity, targetPageIndex: Int) {        // TODO it's not an extension function
    val viewPager = activity.findViewById<ViewPager2>(R.id.viewPager)   // TODO too specific to be in utils
    viewPager.currentItem = targetPageIndex
}