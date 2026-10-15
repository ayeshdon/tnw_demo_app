package com.tnw.demoapp.data.remote

import com.tnw.demoapp.domain.model.WalletAccount
import com.tnw.demoapp.domain.model.WalletTransaction

/**
 * Data access contract for wallet accounts and transactions.
 * A remote implementation can later use Retrofit without changing the repository or UI.
 */
interface WalletApiService {
    suspend fun getAccounts(): List<WalletAccount>
    suspend fun getTransactions(accountId: String): List<WalletTransaction>
}