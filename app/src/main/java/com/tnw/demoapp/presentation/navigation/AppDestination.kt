package com.tnw.demoapp.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe destinations for Jetpack Navigation Compose.
 */
sealed interface AppDestination {
    @Serializable
    data object Login : AppDestination

    @Serializable
    data object Wallet : AppDestination
}
