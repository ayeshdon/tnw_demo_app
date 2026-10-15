package com.tnw.demoapp.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

internal fun formatMoney(amount: Double, currencyCode: String): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
        currency = Currency.getInstance(currencyCode)
        maximumFractionDigits = 2
        minimumFractionDigits = 2
    }
    val value = formatter.format(amount)
    return if (currencyCode == "SGD") value.replace("SGD", "S$") else value
}

internal fun formatDate(timestamp: Long): String =
    SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date(timestamp))
