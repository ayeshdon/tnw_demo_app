package com.tnw.demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tnw.demoapp.di.AppContainer
import com.tnw.demoapp.presentation.WalletApp
import com.tnw.demoapp.presentation.theme.TNWDemoAppTheme

class MainActivity : ComponentActivity() {
    private val appContainer by lazy {
        AppContainer(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TNWDemoAppTheme {
                WalletApp(appContainer)
            }
        }
    }
}
