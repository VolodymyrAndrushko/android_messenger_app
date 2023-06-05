package com.vandrushko.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.vandrushko.ui.fragments.auth.AuthFragmentDirections
import com.vandrushko.R
import com.vandrushko.databinding.FragmentAuthBinding
import com.vandrushko.ui.utils.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


private const val AUTO_LOGIN_DATA_KEY = "SAVED_LOGIN_DATA"

private const val PASSWORD_REGEX =
    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\\\$%^&+=])(?=\\S+\$).{8,}\$"
private const val LOGIN_DATA_KEY = "LOGIN_DATA_KEY"
private const val EMAIL_KEY = "EMAIL_KEY"
private const val PASSWORD_KEY = "PASSWORD_KEY"

class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LOGIN_DATA_KEY)

    private suspend fun saveStringData(key: String, value: String){
        val dataStoreKey = stringPreferencesKey(key)
        requireContext().dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun readStringData(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = requireActivity().dataStore.data.first()

        return preferences[dataStoreKey]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        clearPreferences()
        setEventListeners()

    }

//    private fun clearPreferences() {
//        saveLoginData(SAVED_EMAIL, "")
//        saveLoginData(SAVED_PASSWORD, "")
//    }

    private fun loginOnValidInput(
        email: String,
        password: String,
        saveToSharedPreferences: Boolean
    ): Boolean {
        if (verifyEmail(email) && verifyPassword(password)) {
//            if (saveToSharedPreferences) {
//                saveLoginData(SAVED_EMAIL, email)
//                saveLoginData(SAVED_PASSWORD, password)
//            }
            return true
        }
        return false
    }

//    /**
//     * Method saves login data to sharedPreferences.
//     */
//    private fun saveLoginData(key: String, newValue: String): Unit {
//        val sharedPreference = getSharedPreferences(AUTO_LOGIN_DATA_KEY, Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.putString(key, newValue)
//        editor.apply()
//    }


    private fun verifyEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)) {
            return true
        }
        return false
    }

    private fun verifyPassword(password: String): Boolean {
        if (Regex(PASSWORD_REGEX).find(password) != null && !TextUtils.isEmpty(password)) {
            return true
        }
        return false
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

                if (!verifyEmail(email)) sendTextInputError(
                    emailInputLayout,
                    R.string.email_error_message
                )
                else clearTextInputError(emailInputLayout)

                if (!verifyPassword(password)) sendTextInputError(
                    passwordInputLayout,
                    R.string.password_error_message
                )
                else clearTextInputError(passwordInputLayout)

                if (passwordInputLayout.error == null
                    && emailInputLayout.error == null
                ) {
                    if(binding.rememberCheckBox.isChecked){
                        saveLoginData(email,password)
                    }
                    loginToApp(email)
                }
            }
        }
    }

    private fun saveLoginData(email: String, password: String) {
        lifecycleScope.launch {
            saveStringData(
                EMAIL_KEY,
                email
            )
            saveStringData(
                PASSWORD_KEY,
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
}