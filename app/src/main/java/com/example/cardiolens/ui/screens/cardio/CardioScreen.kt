package com.example.cardiolens.ui.screens.cardio

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardiolens.ui.theme.CardioLensTheme
import com.example.cardiolens.ui.theme.ThemeState

@Composable
fun CardioScreen(
    onNavigateToScan: () -> Unit = {}
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDark = ThemeState.isDarkTheme ?: systemIsDark
    
    val surfaceColor = if (isDark) Color(0xFF141923) else Color.White
    val textColor = if (isDark) Color.White else Color(0xFF191C1D)
    val darkBorder = Color(0xFF2A2F3A)

    // Core Brand Colors (Updated to Crimson)
    val gradientStart = Color(0xFFD22746) // Softer crimson start
    val gradientEnd = Color(0xFF9B112C)   // Deep crimson end
    val brandRed = Color(0xFFB81433)      // Solid crimson

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Changed to Top to manually control precise spacing
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Good morning, User",
                color = textColor.copy(alpha = 0.6f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "How's your heart today?",
                color = textColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Centerpiece
        OrganicMorphingHeart()

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(brandRed)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "READY TO SCAN",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = textColor.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp)) // Increased breathing room

            // Custom Glowing Button Wrapper
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                // FIXED GLOW: Unbounded edge treatment stops the rectangular clipping
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(y = 12.dp)
                        .blur(24.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                        .background(gradientStart.copy(alpha = 0.5f), CircleShape)
                )

                // Actual Button
                Button(
                    onClick = onNavigateToScan,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(gradientStart, gradientEnd)
                                ),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.CameraAlt, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Start Scan", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp)) // Increased breathing room

            // FIXED INFO PILL: Wider and shorter for the true "pill" shape
            Row(
                modifier = Modifier
                    .border(1.dp, if(isDark) darkBorder else Color.Black.copy(alpha = 0.06f), CircleShape)
                    .background(if(isDark) Color(0xFF141923) else Color(0xFFF2F4F5), CircleShape)
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = textColor.copy(alpha = 0.7f))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Hold still • 30 seconds • front camera",
                    fontSize = 12.sp, // Slightly smaller text to fit the thinner pill
                    color = textColor.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp)) // Increased distance to the card

        // Refined Data Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(24.dp),
            border = if (isDark) BorderStroke(1.dp, darkBorder) else BorderStroke(1.dp, Color.Black.copy(alpha = 0.05f)),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isDark) 0.dp else 12.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Internal Pink Accent Orb
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 32.dp, y = (-32).dp)
                        .size(128.dp)
                        .blur(32.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                        .background(brandRed.copy(alpha = if(isDark) 0.15f else 0.1f), CircleShape)
                )

                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text("LAST READING", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor.copy(alpha = 0.6f), letterSpacing = 1.5.sp)
                            Text("Today, 7:42 AM", fontSize = 14.sp, color = textColor.copy(alpha = 0.5f))
                        }

                        Row(
                            modifier = Modifier
                                .background(Color(0xFFE6F4EA), CircleShape)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.size(6.dp).background(Color(0xFF137333), CircleShape))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Normal", color = Color(0xFF137333), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("72", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = textColor, lineHeight = 48.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("bpm", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = textColor.copy(alpha = 0.7f), modifier = Modifier.padding(bottom = 6.dp))
                        }

                        Spacer(modifier = Modifier.width(32.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("42", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = textColor, lineHeight = 48.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Column(modifier = Modifier.padding(bottom = 6.dp)) {
                                Text("ms", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = textColor.copy(alpha = 0.7f))
                                Text("HRV", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = textColor.copy(alpha = 0.7f), letterSpacing = 0.5.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Your heart rate is within a healthy resting range. HRV looks great a sign of good recovery.",
                        fontSize = 14.sp,
                        color = textColor.copy(alpha = 0.8f),
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardioScreenPreview() {
    CardioLensTheme {
        CardioScreen {}
    }
}