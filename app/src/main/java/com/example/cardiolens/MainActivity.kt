package com.example.cardiolens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.cardiolens.ui.components.CardioBottomBar
import com.example.cardiolens.ui.components.CardioTopBar
import com.example.cardiolens.ui.navigation.Destination
import com.example.cardiolens.ui.screens.cardio.CardioScreen
import com.example.cardiolens.ui.screens.settings.SettingsScreen
import com.example.cardiolens.ui.screens.statistics.StatisticsScreen
import com.example.cardiolens.ui.theme.CardioLensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardioLensTheme {

                val navKeyBackStack = rememberNavBackStack(Destination.Cardio)

                @Suppress("UNCHECKED_CAST")
                val backStack = navKeyBackStack as NavBackStack<Destination>

                val currentDestination = backStack.last()

                // We use Surface to ensure the app background updates correctly when the theme toggles
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        topBar = {
                            CardioTopBar()
                        },
                        bottomBar = {
                            CardioBottomBar(
                                currentDestination = currentDestination,
                                onNavigate = { newDestination ->

                                    if (currentDestination != newDestination) {
                                        backStack.clear()
                                        backStack.add(newDestination)
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->

                        NavDisplay(
                            backStack = backStack,
                            modifier = Modifier.padding(innerPadding),
                            entryProvider = { key ->
                                NavEntry(key) {
                                    when (key) {

                                        Destination.Cardio -> CardioScreen(
                                            onNavigateToScan = { }
                                        )

                                        Destination.Stats -> StatisticsScreen()

                                        Destination.Settings -> SettingsScreen()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}