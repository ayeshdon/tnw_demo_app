package com.tnw.demoapp.presentation.wallet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tnw.demoapp.R
import com.tnw.demoapp.domain.model.WalletAccount

@Composable
fun WalletScreen(viewModel: WalletViewModel, onLogout: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { WalletTopBar(onLogout = { viewModel.logout(onLogout) }) }
    ) { padding ->
        WalletContent(
            state = state,
            onAccountSelected = viewModel::selectAccount,
            onShowAccounts = viewModel::showAccounts,
            onRetryAccounts = viewModel::retryAccounts,
            onRetryStatement = viewModel::retryStatement,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun WalletContent(
    state: WalletUiState,
    onAccountSelected: (WalletAccount) -> Unit,
    onShowAccounts: () -> Unit,
    onRetryAccounts: () -> Unit,
    onRetryStatement: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedAccount = state.selectedAccount

    when {
        state.isLoading -> MessageState(
            message = stringResource(R.string.loading_accounts),
            modifier = modifier
        )
        state.errorMessageRes != null -> AccountLoadError(
            errorMessageRes = state.errorMessageRes,
            onRetry = onRetryAccounts,
            modifier = modifier
        )
        selectedAccount != null -> StatementContent(
            account = selectedAccount,
            transactions = state.transactions,
            isLoading = state.isStatementLoading,
            errorMessageRes = state.statementErrorMessageRes,
            onBack = onShowAccounts,
            onRetry = onRetryStatement,
            modifier = modifier
        )
        state.accounts.isEmpty() -> MessageState(
            message = stringResource(R.string.empty_accounts),
            modifier = modifier
        )
        else -> AccountList(
            accounts = state.accounts,
            onAccountSelected = onAccountSelected,
            modifier = modifier
        )
    }
}
