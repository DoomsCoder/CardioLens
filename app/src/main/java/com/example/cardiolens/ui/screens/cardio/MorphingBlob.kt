package com.example.cardiolens.ui.screens.cardio

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardiolens.ui.theme.CardioLensTheme

// Extension function for the 8-point CSS border-radius morphing
@Composable
private fun InfiniteTransition.animateMorphValue(v1: Float, v2: Float, v3: Float): State<Float> {
    return this.animateFloat(
        initialValue = v1,
        targetValue = v1,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 8000
                v1 at 0 using EaseInOut
                v2 at 2720 using EaseInOut // 34%
                v3 at 5360 using EaseInOut // 67%
                v1 at 8000
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "morphValue"
    )
}

@Composable
fun OrganicMorphingHeart(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "blobTransition")

    // 1. Exact CSS X-Axis Border Radii
    val tlX by infiniteTransition.animateMorphValue(0.40f, 0.70f, 1.00f)
    val trX by infiniteTransition.animateMorphValue(0.60f, 0.30f, 0.60f)
    val brX by infiniteTransition.animateMorphValue(0.70f, 0.50f, 0.60f)
    val blX by infiniteTransition.animateMorphValue(0.30f, 0.50f, 1.00f)

    // 2. Exact CSS Y-Axis Border Radii
    val tlY by infiniteTransition.animateMorphValue(0.40f, 0.30f, 1.00f)
    val trY by infiniteTransition.animateMorphValue(0.50f, 0.30f, 1.00f)
    val brY by infiniteTransition.animateMorphValue(0.60f, 0.70f, 0.60f)
    val blY by infiniteTransition.animateMorphValue(0.50f, 0.70f, 0.60f)

    // 3. Heartbeat Pulse Scale
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // 4. Aura Glow Alpha (Restored for higher visibility)
    val auraAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "auraAlpha"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(280.dp) // Added size/padding to ensure the blur isn't clipped
            .padding(24.dp)
    ) {
        // LAYER 1: Restored Blurred Aura Glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.2f) // Pushes the background circle wider than the blob
                .blur(40.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded) // Matches the CSS blur-3xl
                .background(Color(0xFFB81433).copy(alpha = auraAlpha), CircleShape) // Crimson aura
        )

        // LAYER 2: The exact CSS-morphed shape with Stitch's flesh gradient
        Canvas(
            modifier = Modifier
                .size(190.dp)
                .scale(pulseScale)
        ) {
            val w = size.width
            val h = size.height

            val roundRect = RoundRect(
                rect = Rect(0f, 0f, w, h),
                topLeft = CornerRadius(w * tlX, h * tlY),
                topRight = CornerRadius(w * trX, h * trY),
                bottomRight = CornerRadius(w * brX, h * brY),
                bottomLeft = CornerRadius(w * blX, h * blY)
            )

            val path = Path().apply { addRoundRect(roundRect) }

            val fleshGradient = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFD22746), // Brighter crimson center
                    Color(0xFFB81433), // Mid crimson
                    Color(0xFF8B1027)  // Deep crimson edge
                ),
                center = Offset(w * 0.40f, h * 0.40f),
                radius = w * 0.65f
            )

            drawPath(path = path, brush = fleshGradient)
        }

        // LAYER 3: The Custom Vital Signs Wave (No Box)
        Canvas(modifier = Modifier.size(64.dp)) {
            val w = size.width
            val h = size.height

            val wavePath = Path().apply {
                // Flat line start
                moveTo(w * 0.15f, h * 0.5f)
                lineTo(w * 0.35f, h * 0.5f)

                // Q-dip (down)
                lineTo(w * 0.42f, h * 0.65f)

                // R-peak (sharp up)
                lineTo(w * 0.55f, h * 0.25f)

                // S-dip (sharp down)
                lineTo(w * 0.65f, h * 0.55f)

                // Flat line end
                lineTo(w * 0.72f, h * 0.5f)
                lineTo(w * 0.85f, h * 0.5f)
            }

            drawPath(
                path = wavePath,
                color = Color.White.copy(alpha = 0.3f), // Soft translucent white
                style = Stroke(
                    width = 4.dp.toPx(), // Thick, bold line
                    cap = StrokeCap.Round, // Smooth rounded ends
                    join = StrokeJoin.Round // Smooth corners
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrganicMorphingHeartPreview() {
    CardioLensTheme {
        OrganicMorphingHeart()
    }
}