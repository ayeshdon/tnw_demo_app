package com.tnw.demoapp.domain.usecase

import com.tnw.demoapp.domain.model.WalletTransaction
import java.util.concurrent.TimeUnit

class RecentTransactions {
    fun filter(
        transactions: List<WalletTransaction>,
        nowMillis: Long,
        days: Long = 30
    ): List<WalletTransaction> {
        val startMillis = nowMillis - TimeUnit.DAYS.toMillis(days)
        return transactions
            .asSequence()
            .filter { it.occurredAtMillis in startMillis..nowMillis }
            .sortedByDescending(WalletTransaction::occurredAtMillis)
            .toList()
    }
}
