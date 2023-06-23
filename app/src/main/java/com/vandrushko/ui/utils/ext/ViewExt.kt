package com.vandrushko.ui.utils.ext

import android.view.View
import android.view.ViewPropertyAnimator

private const val ALPHA_VALUE = 0.5f

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.animateVisibility(visibility: Int) {
    val animator: ViewPropertyAnimator = when (visibility) {
        View.VISIBLE -> animate().alpha(1f).setDuration(300)
        View.GONE -> animate().alpha(0f).setDuration(300)
        else -> return
    }
    animator.withStartAction {
        this.show()
    }.withEndAction {
        this.visibility = visibility
    }.start()
}

fun View.setTransparentIfNotPresentInContact(value: String?) {
    if (value == null) {
        this.alpha = ALPHA_VALUE
    }
}