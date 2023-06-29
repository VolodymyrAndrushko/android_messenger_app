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
import com.vandrushko.data.DataStoreSingleton
import com.vandrushko.domain.repository.utils.JobState
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
                    is JobState.Success -> {
                        navController.navigate(LoadingScreenFragmentDirections.actionLoadingScreenFragmentToPagerFragment2())
                    }

                    is JobState.Error -> {
                        goToLoginScreen()
                    }

                    is JobState.Loading -> Unit

                    is JobState.Empty -> Unit
                }
            }
        }
    }

    private fun loginAttempt() {
        viewModel.autoLoginAttempt(requireContext())
    }

    private fun goToLoginScreen() {
        navController.navigate(LoadingScreenFragmentDirections.actionLoadingScreenFragmentToLoginFragment())
    }
}