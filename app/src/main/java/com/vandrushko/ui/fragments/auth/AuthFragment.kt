package com.vandrushko.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.vandrushko.R
import com.vandrushko.databinding.FragmentAuthBinding
import com.vandrushko.ui.fragments.Configs
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.DataStoreSingleton
import com.vandrushko.ui.utils.Matcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


private const val AUTO_LOGIN_DATA_KEY = "SAVED_LOGIN_DATA"


class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEventListeners()
    }

    private fun setEventListeners(): Unit {
        setRegisterButtonOnClickListener()
        setGoogleButtonOnClickListener()
    }

    private fun setGoogleButtonOnClickListener() {
        binding.googleButton.setOnClickListener {
            loginToApp(null)
        }
    }

    private fun setRegisterButtonOnClickListener(): Unit {
        with(binding) {
            registerButton.setOnClickListener {
                val email: String = emailInputLayout.editText?.text.toString()
                val password: String = passwordInputLayout.editText?.text.toString()

                if (isValidLoginData(email, password)) {
                    if (binding.rememberCheckBox.isChecked) {
                        saveLoginData(email, password)
                    }
                    loginToApp(email)
                }
            }
        }
    }

    private fun isValidLoginData(email: String, password: String): Boolean {
        val matcher: Matcher = Matcher()
        with(binding) {
            with(matcher) {
                if (!isValidEmail(email) || !isValidPassword(password)) {
                    if (!isValidEmail(email)) {
                        sendTextInputError(
                            binding.emailInputLayout,
                            R.string.email_error_message
                        )
                    } else {
                        clearTextInputError(emailInputLayout)
                    }
                    if (!isValidPassword(password)) {
                        sendTextInputError(
                            passwordInputLayout,
                            R.string.password_error_message
                        )
                    } else {
                        clearTextInputError(passwordInputLayout)
                    }
                    return false
                } else {
                    clearInputFields()
                }
            }
        }
        return true
    }

    private fun clearInputFields() {
        clearTextInputError(binding.emailInputLayout)
        clearTextInputError(binding.passwordInputLayout)
    }

    private fun saveLoginData(email: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreSingleton.
            saveStringData(
                requireContext(),
                Configs.EMAIL_KEY,
                email
            )
            DataStoreSingleton.
            saveStringData(
                requireContext(),
                Configs.PASSWORD_KEY,
                password
            )
        }
    }


    private fun loginToApp(email: String?) {
        val action = AuthFragmentDirections.actionAuthFragmentToPagerFragment2(email)
        navController.navigate(action)
    }

    private fun sendTextInputError(input: TextInputLayout, emailErrorMessage: Int): Unit {
        input.error = getString(emailErrorMessage)
    }

    private fun clearTextInputError(input: TextInputLayout): Unit {
        input.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.cancel()
    }
}