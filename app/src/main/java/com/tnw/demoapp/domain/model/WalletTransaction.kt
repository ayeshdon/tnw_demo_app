package com.tnw.demoapp.domain.model

enum class TransactionType {
    PAYMENT,
    DEPOSIT
}

data class WalletTransaction(
    val id: String,
    val accountId: String,
    val occurredAtMillis: Long,
    val description: String,
    val amount: Double,
    val type: TransactionType,
    val balanceAfter: Double
)
