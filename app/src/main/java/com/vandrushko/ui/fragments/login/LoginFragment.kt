package com.vandrushko.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.vandrushko.data.model.UserRequest
import com.vandrushko.databinding.FragmentLoginBinding
import com.vandrushko.ui.utils.BaseFragment

class LoginFragment(
) : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        setSignUpOnClickListener()
        setLoginButtonOnClickListener()
    }

    private fun setSignUpOnClickListener() {
        binding.signUpText.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToAuthFragment())
        }
    }

    private fun setLoginButtonOnClickListener() {
        with(binding) {
            loginButton.setOnClickListener {
                viewModel.loginUser(
                    UserRequest(
                        emailInputLayout.editText?.text.toString(),
                        passwordInputLayout.editText?.text.toString()
                    ))
            }
        }
    }
}