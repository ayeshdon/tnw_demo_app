package com.tnw.demoapp.data.mockapis

import com.tnw.demoapp.data.remote.WalletApiService
import com.tnw.demoapp.domain.model.TransactionType
import com.tnw.demoapp.domain.model.WalletAccount
import com.tnw.demoapp.domain.model.WalletTransaction
import kotlinx.coroutines.delay
import java.util.Calendar

class MockWalletApiService : WalletApiService {
    override suspend fun getAccounts(): List<WalletAccount> = delay(1000).let {
        listOf(
            WalletAccount("usd", "USD Account", "USD", 12_450.00),
            WalletAccount("eur", "EUR Account", "EUR", 4_820.00),
            WalletAccount("sgd", "SGD Account", "SGD", 8_250.00)
        )
    }

    override suspend fun getTransactions(accountId: String): List<WalletTransaction> {
        delay(1000)
        val account = getAccounts().firstOrNull { it.id == accountId } ?: return emptyList()
        val now = System.currentTimeMillis()
        return listOf(
            transaction(
                account,
                "payment-1",
                1,
                "Online purchase",
                120.00,
                TransactionType.PAYMENT,
                now
            ),
            transaction(
                account,
                "deposit-1",
                3,
                "Salary deposit",
                500.00,
                TransactionType.DEPOSIT,
                now
            ),
            transaction(
                account,
                "payment-2",
                7,
                "Grocery store",
                80.00,
                TransactionType.PAYMENT,
                now
            ),
            transaction(
                account,
                "payment-3",
                14,
                "Subscription",
                15.99,
                TransactionType.PAYMENT,
                now
            ),
            transaction(
                account,
                "deposit-2",
                21,
                "Wallet top-up",
                250.00,
                TransactionType.DEPOSIT,
                now
            ),
            transaction(account, "payment-4", 28, "Transport", 32.50, TransactionType.PAYMENT, now),
            transaction(
                account,
                "payment-old",
                45,
                "Older transaction",
                10.00,
                TransactionType.PAYMENT,
                now
            )
        )
    }

    private fun transaction(
        account: WalletAccount,
        id: String,
        daysAgo: Int,
        description: String,
        amount: Double,
        type: TransactionType,
        nowMillis: Long
    ): WalletTransaction {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = nowMillis
            add(Calendar.DAY_OF_YEAR, -daysAgo)
        }
        return WalletTransaction(
            id = "${account.id}-$id",
            accountId = account.id,
            occurredAtMillis = calendar.timeInMillis,
            description = description,
            amount = amount,
            type = type,
            balanceAfter = account.balance
        )
    }
}
