package com.tnw.demoapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tnw.demoapp.di.AppContainer
import com.tnw.demoapp.presentation.login.LoginScreen
import com.tnw.demoapp.presentation.login.LoginViewModel
import com.tnw.demoapp.presentation.navigation.AppDestination
import com.tnw.demoapp.presentation.wallet.WalletScreen
import com.tnw.demoapp.presentation.wallet.WalletViewModel

@Composable
fun WalletApp(appContainer: AppContainer) {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel(
        factory = appContainer.loginViewModelFactory
    )
    val loginState by loginViewModel.uiState.collectAsState()
    val startDestination = remember {
        if (loginState.isAuthenticated) AppDestination.Wallet else AppDestination.Login
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable<AppDestination.Login> {
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate(AppDestination.Wallet) {
                            popUpTo<AppDestination.Login> { inclusive = true }
                        }
                    }
                )
            }
            composable<AppDestination.Wallet> {
                val walletViewModel: WalletViewModel = viewModel(
                    factory = appContainer.walletViewModelFactory
                )
                WalletScreen(
                    viewModel = walletViewModel,
                    onLogout = {
                        loginViewModel.logout()
                        walletViewModel.showAccounts()
                        navController.navigate(AppDestination.Login) {
                            popUpTo<AppDestination.Wallet> { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
