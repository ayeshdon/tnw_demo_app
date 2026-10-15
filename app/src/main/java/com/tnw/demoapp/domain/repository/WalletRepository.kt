package com.tnw.demoapp.domain.repository

import com.tnw.demoapp.domain.model.WalletAccount
import com.tnw.demoapp.domain.model.WalletTransaction

interface WalletRepository {
    suspend fun getAccounts(): List<WalletAccount>
    suspend fun getTransactions(accountId: String): List<WalletTransaction>
}
