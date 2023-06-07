package com.vandrushko.ui.fragments.loadingScreen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.vandrushko.databinding.FragmentLoadingScreenBinding
import com.vandrushko.ui.fragments.Configs
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.DataStoreSingleton
import com.vandrushko.ui.utils.Matcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class LoadingScreenFragment :
    BaseFragment<FragmentLoadingScreenBinding>(FragmentLoadingScreenBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginAttempt()
    }

    private fun loginAttempt() {
        lifecycleScope.launch {
            val email = DataStoreSingleton.readStringData(
                requireContext(),
                Configs.EMAIL_KEY,
            )
            val password = DataStoreSingleton.readStringData(
                requireContext(),
                Configs.PASSWORD_KEY,
            )

            val action =
                if (isValidLoginData(email, password)) {
                    LoadingScreenFragmentDirections.actionLoadingScreenFragmentToPagerFragment2(
                        email
                    )
                } else {
                    LoadingScreenFragmentDirections.actionLoadingScreenFragmentToAuthFragment2()
                }
            navController.navigate(action)
        }
    }

    private fun isValidLoginData(email: String?, password: String?): Boolean {
        val matcher = Matcher()
        return matcher.isValidEmail(email) && matcher.isValidPassword(password)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}