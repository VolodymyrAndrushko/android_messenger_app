package com.vandrushko.ui.fragments.loadingScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.vandrushko.data.model.UserRequest
import com.vandrushko.databinding.FragmentLoadingScreenBinding
import com.vandrushko.ui.fragments.Configs
import com.vandrushko.ui.fragments.login.LoginViewModel
import com.vandrushko.ui.utils.BaseFragment
import com.vandrushko.ui.utils.DataStoreSingleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadingScreenFragment :
    BaseFragment<FragmentLoadingScreenBinding>(FragmentLoadingScreenBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        loginAttempt()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.userStateFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when (it) {
                    is LoginViewModel.LoginState.Success -> {
                        navController.navigate(LoadingScreenFragmentDirections.actionLoadingScreenFragmentToPagerFragment2())
                    }

                    is LoginViewModel.LoginState.Error -> {
                        goToLoginScreen()
                    }

                    is LoginViewModel.LoginState.Loading -> Unit

                    is LoginViewModel.LoginState.Empty -> Unit
                }
            }
        }
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

            viewModel.loginUser(UserRequest(email, password))
        }
    }

    private fun goToLoginScreen() {
        navController.navigate(LoadingScreenFragmentDirections.actionLoadingScreenFragmentToLoginFragment())
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}