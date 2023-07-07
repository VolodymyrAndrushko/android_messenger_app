package com.vandrushko.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.vandrushko.R
import com.vandrushko.data.model.UserRequest
import com.vandrushko.databinding.FragmentAuthBinding
import com.vandrushko.ui.utils.JobState
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.Matcher
import com.vandrushko.ui.utils.ext.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setEventListeners()

//        val db = Room.databaseBuilder(requireContext(), UserDataBase::class.java, "user_db").build()
//
//        GlobalScope.launch(Dispatchers.IO) {
//            db.userDao().insetUserData(UserData(Contact(),"asdasd","asdas12312123"))
//        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.registerState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when (it) {
                    is JobState.Success -> {
                        loginToApp()
                    }


                    is JobState.Loading -> {

                    }

                    is JobState.Error -> {
                        binding.root.showErrorSnackBar(requireContext(), it.error)
                    }

                    is JobState.Empty -> Unit

                }
            }
        }
    }

    private fun setEventListeners() {
        setRegisterButtonOnClickListener()
        setGoogleButtonOnClickListener()
        setGoToLoginPageOnClickListener()
    }

    private fun setGoToLoginPageOnClickListener() {
        binding.signInText.setOnClickListener {
            navController.navigate(AuthFragmentDirections.actionAuthFragmentToLoginFragment())
        }
    }

    private fun setGoogleButtonOnClickListener() {
        binding.googleButton.setOnClickListener {
            loginToApp()
        }
    }

    private fun setRegisterButtonOnClickListener() {
        with(binding) {
            registerButton.setOnClickListener {
                val email: String = emailInputLayout.editText?.text.toString()
                val password: String = passwordInputLayout.editText?.text.toString()

                if (isValidLoginData(email, password)) {
                    viewModel.registerUser(
                        UserRequest(email, password),
                        requireContext(),
                        rememberCheckBox.isChecked
                    )
                }
            }
        }
    }

    private fun isValidLoginData(email: String, password: String): Boolean {
        val isValidEmail = Matcher.isValidEmail(email)
        val isValidPassword = Matcher.isValidPassword(password)
        with(binding) {
            when {
                isValidEmail && isValidPassword -> {
                    clearInputFields()
                    return true
                }

                !isValidEmail && !isValidPassword -> {
                    sendTextInputError(
                        binding.emailInputLayout,
                        R.string.email_error_message
                    )
                    sendTextInputError(
                        passwordInputLayout,
                        R.string.password_error_message
                    )
                }

                !isValidEmail -> {
                    sendTextInputError(
                        binding.emailInputLayout,
                        R.string.email_error_message
                    )
                }

                else -> {
                    sendTextInputError(
                        passwordInputLayout,
                        R.string.password_error_message
                    )
                }
            }
        }
        return false
    }

    private fun clearInputFields() {
        clearTextInputError(binding.emailInputLayout)
        clearTextInputError(binding.passwordInputLayout)
    }

    private fun loginToApp() {
        navController.navigate(AuthFragmentDirections.actionAuthFragmentToPagerFragment2())
    }

    private fun sendTextInputError(input: TextInputLayout, emailErrorMessage: Int) {
        input.error = getString(emailErrorMessage)
    }

    private fun clearTextInputError(input: TextInputLayout) {
        input.error = null
    }

}