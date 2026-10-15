package com.tnw.demoapp.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tnw.demoapp.data.remote.AuthApiService
import com.tnw.demoapp.data.repository.AuthenticationRepositoryImpl
import com.tnw.demoapp.data.mockapis.MockAuthApiService
import com.tnw.demoapp.data.mockapis.MockWalletApiService
import com.tnw.demoapp.data.repository.WalletRepositoryImpl
import com.tnw.demoapp.data.local.AuthPreferencesRepository
import com.tnw.demoapp.data.local.SharedPrefAuthPreferences
import com.tnw.demoapp.domain.repository.AuthenticationRepository
import com.tnw.demoapp.domain.repository.WalletRepository
import com.tnw.demoapp.domain.usecase.RecentTransactions
import com.tnw.demoapp.presentation.login.LoginViewModel
import com.tnw.demoapp.presentation.wallet.WalletViewModel

class AppContainer(context: Context) {
    private val appContext = context.applicationContext

    private val authApiService: AuthApiService by lazy {
        MockAuthApiService()
    }

    private val authenticationRepository: AuthenticationRepository by lazy {
        AuthenticationRepositoryImpl(authApiService)
    }

    private val walletDataSource by lazy {
        MockWalletApiService()
    }

    private val walletRepository: WalletRepository by lazy {
        WalletRepositoryImpl(walletDataSource)
    }

    private val recentTransactions by lazy {
        RecentTransactions()
    }

    val authPreferences: AuthPreferencesRepository by lazy {
        SharedPrefAuthPreferences(appContext)
    }

    val loginViewModelFactory: ViewModelProvider.Factory by lazy {
        viewModelFactory {
            LoginViewModel(authenticationRepository, authPreferences)
        }
    }

    val walletViewModelFactory: ViewModelProvider.Factory by lazy {
        viewModelFactory { WalletViewModel(walletRepository, recentTransactions) }
    }

    private inline fun <reified T : ViewModel> viewModelFactory(
        crossinline create: () -> T
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
            require(modelClass.isAssignableFrom(T::class.java)) {
                "Unsupported ViewModel type: ${modelClass.name}"
            }
            return modelClass.cast(create())
                ?: error("Unable to create ${T::class.simpleName}.")
        }
    }
}
