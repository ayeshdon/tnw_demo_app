package com.tnw.demoapp

import com.tnw.demoapp.data.mockapis.MockWalletApiService
import com.tnw.demoapp.data.repository.WalletRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WalletRepositoryTest {
    @Test
    fun retrievesWalletAccountsFromDataSource() = runBlocking {
        val accounts = WalletRepositoryImpl(MockWalletApiService()).getAccounts()

        assertEquals(3, accounts.size)
        assertTrue(accounts.map { it.currencyCode }.containsAll(listOf("USD", "EUR", "SGD")))
    }
}
