package com.example.cardiolens.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardiolens.ui.theme.CardioLensTheme
import com.example.cardiolens.ui.theme.ThemeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardioTopBar() {
    val systemIsDark = isSystemInDarkTheme()
    val isDark = ThemeState.isDarkTheme ?: systemIsDark

    // Brand colors based on theme
    val brandRed = Color(0xFFB81433) // Crimson red for light mode
    val lightPinkLogo = Color(0xFFF4A4B5) // Muted soft pink for dark mode

    val logoColor = if (isDark) lightPinkLogo else brandRed
    val titleColor = if (isDark) lightPinkLogo else brandRed
    val iconTint = if (isDark) Color.White.copy(alpha = 0.7f) else Color(0xFF191C1D).copy(alpha = 0.7f)

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "CardioLens",
                fontWeight = FontWeight.Bold,
                color = titleColor,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            // We use an Icon directly instead of wrapping it in an IconButton
            // since this is just a logo and doesn't need to be clickable (which creates the ripple/circle)
            Box(modifier = Modifier.padding(12.dp)) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Logo",
                    tint = logoColor
                )
            }
        },
        actions = {
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null, // This completely removes the ripple/gray circle
                        onClick = { ThemeState.isDarkTheme = !isDark }
                    )
            ) {
                Icon(
                    imageVector = if (isDark) Icons.Outlined.LightMode else Icons.Outlined.Nightlight,
                    contentDescription = "Toggle Theme",
                    tint = iconTint
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun CardioTopBarPreview() {
    CardioLensTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CardioTopBar()
        }
    }
}