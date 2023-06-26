package com.vandrushko.ui.fragments.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.vandrushko.R
import com.vandrushko.data.model.UserRequest
import com.vandrushko.databinding.FragmentLoginBinding
import com.vandrushko.ui.fragments.login.LoginViewModel.LoginState
import com.vandrushko.ui.fragments.login.LoginViewModel.LoginState.Loading
import com.vandrushko.ui.fragments.login.LoginViewModel.LoginState.Success
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.DataStoreSingleton
import com.vandrushko.ui.utils.ext.hide
import com.vandrushko.ui.utils.ext.show
import com.vandrushko.ui.utils.ext.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.userStateFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                hideLoading()
                when (it) {
                    is Success -> {
                        loginToApp()
                    }

                    is LoginState.Error -> {
                        binding.root.showErrorSnackBar(requireContext(),it.error)
                    }

                    is Loading -> {
                        showLoading()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressBarLogin.hide()
            loginButton.text = getString(R.string.login)
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBarLogin.show()
            loginButton.text = ""
        }
    }

    private fun loginToApp() {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToPagerFragment())
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
                val email = emailInputLayout.editText?.text.toString()
                val password = passwordInputLayout.editText?.text.toString()

                viewModel.loginUser(
                    UserRequest(
                        email,
                        password
                    )
                )
                if(rememberCheckBox.isChecked){
                    lifecycleScope.launch(Dispatchers.IO) {
                        DataStoreSingleton.saveLoginData(requireContext(), email, password)
                    }
                }
            }
        }
    }
}