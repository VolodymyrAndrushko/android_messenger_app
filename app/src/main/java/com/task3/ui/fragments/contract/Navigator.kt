package com.task3.ui.fragments.contract

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.task3.domain.dataclass.Contact

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun showContactsScreen()

    fun showContactProfileScreen(contact: Contact)

    fun goBack()

    fun <T : Parcelable> publishResult(result: T)

    fun <T : Parcelable> listenResult(clazz : Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)
}