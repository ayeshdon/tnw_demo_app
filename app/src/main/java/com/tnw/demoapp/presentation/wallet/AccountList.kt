package com.tnw.demoapp.presentation.wallet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tnw.demoapp.R
import com.tnw.demoapp.domain.model.WalletAccount
import com.tnw.demoapp.utils.formatMoney

@Composable
internal fun AccountList(
    accounts: List<WalletAccount>,
    onAccountSelected: (WalletAccount) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 8.dp)) {
                Text(
                    stringResource(R.string.header_your_accounts),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    stringResource(R.string.subtitle_your_accounts),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        items(accounts, key = WalletAccount::id) { account ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { onAccountSelected(account) }
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(account.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        formatMoney(account.balance, account.currencyCode),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        stringResource(R.string.action_view_last_30_days),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
