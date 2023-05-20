package com.shpp.android.task2.ui.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.task2.R
import com.example.task2.databinding.ActivityAuthBinding
import com.google.android.material.textfield.TextInputLayout


private const val AUTO_LOGIN_DATA_KEY = "saved_login_data"

private const val PASSWORD_REGEX =
    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\\\$%^&+=])(?=\\S+\$).{8,}\$"
private const val SAVED_EMAIL = "saved_email"
private const val SAVED_PASSWORD = "saved_password"

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        clearPreferences()
        autoLoginAttempt()
        setEventListeners()

    }

    /**
     * Method clears sharedPreferences login data.
     */
    private fun clearPreferences() {
        saveLoginData(SAVED_EMAIL, "")
        saveLoginData(SAVED_PASSWORD, "")
    }

    /**
     * Method tries to login with savedPreferences login data.
     */
    private fun autoLoginAttempt(): Unit {
        val loginData = getSharedPreferences(AUTO_LOGIN_DATA_KEY, Context.MODE_PRIVATE)
        val savedEmail: String = loginData.getString(SAVED_EMAIL, null) ?: return
        val savedPassword: String = loginData.getString(SAVED_PASSWORD, null) ?: return

        if (loginOnValidInput(savedEmail, savedPassword, false)) {
            finish()
        }
    }

    /**
     * Method checks input email and password strings, if they valid -> return true and
     * intent to new Activity.
     * @param saveToSharedPreferences if parameter is true -> save data to sharedPreferences.
     */
    private fun loginOnValidInput(
        email: String,
        password: String,
        saveToSharedPreferences: Boolean
    ): Boolean {
        if (verifyEmail(email) && verifyPassword(password)) {
//            val intent = Intent(this, MainActivity::class.java)
//
//            intent.putExtra(Configs.emailIntentKey, email)
//            startActivity(intent)

            overridePendingTransition(
                com.google.android.material.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out
            )

            if (saveToSharedPreferences) {
                saveLoginData(SAVED_EMAIL, email)
                saveLoginData(SAVED_PASSWORD, password)
            }
            return true
        }
        return false
    }

    /**
     * Method saves login data to sharedPreferences.
     */
    private fun saveLoginData(key: String, newValue: String): Unit {
        val sharedPreference = getSharedPreferences(AUTO_LOGIN_DATA_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(key, newValue)
        editor.apply()
    }

    /**
     * Method verifies email string with Patterns Regex and return true if it is valid.
     */
    private fun verifyEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)) {
            return true
        }
        return false
    }

    /**
     * Method verifies password string with Regex and return true if it is valid.
     */
    private fun verifyPassword(password: String): Boolean {
        if (Regex(PASSWORD_REGEX).find(password) != null && !TextUtils.isEmpty(password)) {
            return true
        }
        return false
    }

    /**
     * Method set Activity event listeners.
     */
    private fun setEventListeners(): Unit {
        setRegisterButtonOnClickListener()
        setGoogleButtonOnClickListener()
    }

    private fun setGoogleButtonOnClickListener() {
        binding.googleButton.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Method sets register button's on click listener.
     */
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

                if (loginOnValidInput(email, password, rememberCheckBox.isChecked)) {
                    finish()
                }
            }
        }

    }

    /**
     * Method sets TextInput layout error.
     */
    private fun sendTextInputError(input: TextInputLayout, emailErrorMessage: Int): Unit {
        input.error = getString(emailErrorMessage)
//
    }

    /**
     * Method clears TextInput layout error.
     */
    private fun clearTextInputError(input: TextInputLayout): Unit {
        input.error = null
    }
}