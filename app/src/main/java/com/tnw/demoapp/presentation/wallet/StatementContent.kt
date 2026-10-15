package com.tnw.demoapp.presentation.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tnw.demoapp.R
import com.tnw.demoapp.domain.model.TransactionType
import com.tnw.demoapp.domain.model.WalletAccount
import com.tnw.demoapp.domain.model.WalletTransaction
import com.tnw.demoapp.utils.formatDate
import com.tnw.demoapp.utils.formatMoney

@Composable
internal fun StatementContent(
    account: WalletAccount,
    transactions: List<WalletTransaction>,
    isLoading: Boolean,
    errorMessageRes: Int?,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        ) {
            Text(stringResource(R.string.action_all_accounts))
        }
        Column(Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
            Text(account.name, style = MaterialTheme.typography.headlineSmall)
            Text(
                formatMoney(account.balance, account.currencyCode),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                stringResource(R.string.subtitle_statement),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        when {
            isLoading -> MessageState(
                stringResource(R.string.loading_statement),
                Modifier.weight(1f)
            )
            errorMessageRes != null -> Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(errorMessageRes), color = MaterialTheme.colorScheme.error)
                Button(onClick = onRetry) {
                    Text(stringResource(R.string.action_try_again))
                }
            }
            transactions.isEmpty() -> MessageState(
                stringResource(R.string.empty_transactions),
                Modifier.weight(1f)
            )
            else -> LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transactions, key = WalletTransaction::id) { transaction ->
                    TransactionRow(transaction, account.currencyCode)
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(transaction: WalletTransaction, currencyCode: String) {
    val amountPrefix = if (transaction.type == TransactionType.DEPOSIT) "+" else "−"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            formatDate(transaction.occurredAtMillis),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(transaction.description, style = MaterialTheme.typography.bodyLarge)
                Text(
                    stringResource(
                        R.string.label_balance,
                        formatMoney(transaction.balanceAfter, currencyCode)
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                "$amountPrefix${formatMoney(transaction.amount, currencyCode)}",
                fontWeight = FontWeight.Medium
            )
        }
    }
}
