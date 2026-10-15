package com.tnw.demoapp.data.remote

import com.tnw.demoapp.domain.model.WalletAccount
import com.tnw.demoapp.domain.model.WalletTransaction

interface WalletApiService {
    suspend fun getAccounts(): List<WalletAccount>
    suspend fun getTransactions(accountId: String): List<WalletTransaction>
}