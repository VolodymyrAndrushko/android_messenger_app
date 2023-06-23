package com.vandrushko.ui.utils.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vandrushko.R

fun ImageView.loadImage(image: String? = null) {
    Glide.with(this)
        .load(image)
        .centerCrop()
        .circleCrop()
        .error(
            Glide.with(this)
                .load(R.drawable.ic_default_profile_photo)
                .centerCrop()
                .circleCrop()
        )
        .into(this)
}