package com.tnw.demoapp.presentation.wallet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tnw.demoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletTopBar(onLogout: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            TextButton(onClick = onLogout) {
                Text(
                    text = stringResource(R.string.action_logout),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    )
}
