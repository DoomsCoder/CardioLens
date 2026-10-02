package com.example.cardiolens.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardiolens.ui.navigation.Destination
import com.example.cardiolens.ui.theme.CardioLensTheme
import com.example.cardiolens.ui.theme.ThemeState

@Composable
fun CardioBottomBar(
    currentDestination: Destination,
    onNavigate: (Destination) -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDark = ThemeState.isDarkTheme ?: systemIsDark

    val brandRed = Color(0xFFB81433) // Crimson red
    val surfaceColor = if (isDark) Color(0xFF141923) else Color.White
    val unselectedIconColor = if (isDark) Color.White.copy(alpha = 0.5f) else Color(0xFF6B7280)

    Surface(
        color = surfaceColor.copy(alpha = 0.95f),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart =24.dp, topEnd = 24.dp)),
        shadowElevation = if (isDark) 0.dp else 16.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            Destination.entries.forEach { destination ->
                val isSelected = currentDestination == destination

                val label = when(destination) {
                    Destination.Cardio -> "Cardio"
                    Destination.Stats -> "Stats"
                    Destination.Settings -> "Settings"
                }

                val icon = when(destination) {
                    Destination.Cardio -> Icons.Filled.MonitorHeart
                    Destination.Stats -> Icons.Outlined.Analytics
                    Destination.Settings -> Icons.Outlined.Settings
                }

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            onNavigate(destination)
                        }
                    },
                    icon =  { Icon(icon, contentDescription = label) },
                    label = { Text(label) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        // Fix: The text sits below the red indicator pill. 
                        // So we make it brandRed so it's visible against the white/dark background.
                        selectedTextColor = brandRed, 
                        indicatorColor = brandRed,
                        unselectedIconColor = unselectedIconColor,
                        unselectedTextColor = unselectedIconColor
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardioBottomBarPreview() {
    CardioLensTheme {
        CardioBottomBar(
            currentDestination = Destination.Cardio,
            onNavigate = {}
        )
    }
}