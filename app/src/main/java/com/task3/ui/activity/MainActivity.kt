package com.task3.ui.activity

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import com.task3.R
import com.task3.databinding.ActivityMainBinding
import com.task3.domain.dataclass.Contact
import com.task3.ui.fragments.contacts.ContactsFragment
import com.task3.ui.fragments.contract.Navigator
import com.task3.ui.fragments.contract.ResultListener
import com.task3.ui.fragments.profile.ProfileFragment

//class MainActivity : AppCompatActivity(), Navigator {
//    private var _binding: ActivityMainBinding? = null
//    private val binding: ActivityMainBinding get() = requireNotNull(_binding)
//    private val currentFragment: Fragment
//        get() = supportFragmentManager.findFragmentById(R.id.activityFragmentContainer)!!
//
//    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
//        override fun onFragmentViewCreated(
//            fm: FragmentManager,
//            f: Fragment,
//            v: View,
//            savedInstanceState: Bundle?
//        ) {
//            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        showContactsScreen()
//
//        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
//    }
//
//    override fun showContactsScreen() {
//        launchFragment(ContactsFragment())
//    }
//
//    override fun showContactProfileScreen(contact: Contact) {
//        launchFragment(ProfileFragment.newInstance(contact))
//    }
//
//    override fun goBack() {
//        supportFragmentManager.popBackStack()
//    }
//
//    override fun onBackPressed() {
//        if (supportFragmentManager.backStackEntryCount <= 1) {
//            finish()
//        } else goBack()
//    }
//
//    override fun <T : Parcelable> publishResult(result: T) {
//        supportFragmentManager.setFragmentResult(
//            result.javaClass.name,
//            bundleOf(KEY_RESULT to result)
//        )
//    }
//
//    override fun <T : Parcelable> listenResult(
//        clazz: Class<T>,
//        owner: LifecycleOwner,
//        listener: ResultListener<T>
//    ) {
//        supportFragmentManager.setFragmentResultListener(
//            clazz.name,
//            owner,
//            FragmentResultListener { key, bundle ->
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    listener.invoke(bundle.getParcelable(key, clazz)!!)
//                }
//            })
//    }
//
//    private fun launchFragment(fragment: Fragment) {
//        supportFragmentManager
//            .beginTransaction()
//            .addToBackStack(null)
//            .replace(R.id.activityFragmentContainer, fragment)
//            .commit()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
//    }
//
//    companion object {
//        @JvmStatic
//        private val KEY_RESULT = "RESULT"
//    }
//}

class MainActivity : AppCompatActivity(R.layout.activity_main)
