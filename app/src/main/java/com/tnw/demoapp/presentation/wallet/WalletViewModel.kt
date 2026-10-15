package com.tnw.demoapp.presentation.wallet

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tnw.demoapp.R
import com.tnw.demoapp.domain.model.WalletAccount
import com.tnw.demoapp.domain.model.WalletTransaction
import com.tnw.demoapp.domain.repository.WalletRepository
import com.tnw.demoapp.domain.usecase.RecentTransactions
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WalletUiState(
    val isLoading: Boolean = true,
    val accounts: List<WalletAccount> = emptyList(),
    val selectedAccount: WalletAccount? = null,
    val isStatementLoading: Boolean = false,
    val transactions: List<WalletTransaction> = emptyList(),
    @param:StringRes val errorMessageRes: Int? = null,
    @param:StringRes val statementErrorMessageRes: Int? = null
)

class WalletViewModel(
    private val walletRepository: WalletRepository,
    private val recentTransactions: RecentTransactions = RecentTransactions()
) : ViewModel() {
    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAccounts()
    }

    fun selectAccount(account: WalletAccount) {
        _uiState.value = _uiState.value.copy(
            selectedAccount = account,
            isStatementLoading = true,
            transactions = emptyList(),
            statementErrorMessageRes = null
        )
        viewModelScope.launch {
            try {
                val allTransactions = walletRepository.getTransactions(account.id)
                if (_uiState.value.selectedAccount?.id == account.id) {
                    _uiState.value = _uiState.value.copy(
                        isStatementLoading = false,
                        transactions =
                            recentTransactions.filter(
                                allTransactions,
                                System.currentTimeMillis()
                            )
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                if (_uiState.value.selectedAccount?.id == account.id) {
                    _uiState.value = _uiState.value.copy(
                        isStatementLoading = false,
                        statementErrorMessageRes = R.string.error_loading_statement
                    )
                }
            }
        }
    }

    fun showAccounts() {
        _uiState.value = _uiState.value.copy(
            selectedAccount = null,
            transactions = emptyList(),
            statementErrorMessageRes = null
        )
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        showAccounts()
        onLogoutSuccess()
    }

    fun retryStatement() {
        _uiState.value.selectedAccount?.let(::selectAccount)
    }

    fun retryAccounts() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessageRes = null)
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            try {
                val accounts = walletRepository.getAccounts()
                _uiState.value = _uiState.value.copy(isLoading = false, accounts = accounts)
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessageRes = R.string.error_loading_accounts_retry
                )
            }
        }
    }
}
