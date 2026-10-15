package com.tnw.demoapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {
    @Serializable
    data object Login : AppDestination

    @Serializable
    data object Wallet : AppDestination
}
