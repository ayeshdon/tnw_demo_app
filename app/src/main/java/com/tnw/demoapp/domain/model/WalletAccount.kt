package com.tnw.demoapp.domain.model

data class WalletAccount(
    val id: String,
    val name: String,
    val currencyCode: String,
    val balance: Double
)
