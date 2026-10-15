package com.tnw.demoapp.data.repository

import com.tnw.demoapp.data.remote.WalletApiService
import com.tnw.demoapp.domain.model.WalletAccount
import com.tnw.demoapp.domain.model.WalletTransaction
import com.tnw.demoapp.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val dataSource: WalletApiService
) : WalletRepository {
    override suspend fun getAccounts(): List<WalletAccount> = dataSource.getAccounts()

    override suspend fun getTransactions(accountId: String): List<WalletTransaction> =
        dataSource.getTransactions(accountId)
}
