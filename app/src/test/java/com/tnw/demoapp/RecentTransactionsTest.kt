package com.tnw.demoapp

import com.tnw.demoapp.domain.model.TransactionType
import com.tnw.demoapp.domain.model.WalletTransaction
import com.tnw.demoapp.domain.usecase.RecentTransactions
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

class RecentTransactionsTest {
    private val now = TimeUnit.DAYS.toMillis(100)

    @Test
    fun filtersToThirtyDaysAndSortsNewestFirst() {
        val transactions = listOf(
            transaction("older", now - TimeUnit.DAYS.toMillis(31)),
            transaction("oldest-recent", now - TimeUnit.DAYS.toMillis(30)),
            transaction("newest", now - TimeUnit.DAYS.toMillis(1)),
            transaction("future", now + 1)
        )

        val result = RecentTransactions().filter(transactions, now)

        assertEquals(listOf("newest", "oldest-recent"), result.map(WalletTransaction::id))
    }

    private fun transaction(id: String, timestamp: Long) = WalletTransaction(
        id = id,
        accountId = "usd",
        occurredAtMillis = timestamp,
        description = id,
        amount = 1.0,
        type = TransactionType.PAYMENT,
        balanceAfter = 10.0
    )
}
