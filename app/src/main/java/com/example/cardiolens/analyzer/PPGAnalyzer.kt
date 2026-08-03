package com.example.cardiolens.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

/**
 * PPGAnalyzer implements the first stage of the CardioLens pipeline.
 * It extracts the average green channel value from the image buffer provided by CameraX.
 */
class PPGAnalyzer(
    private val onGreenChannelAverage: (Double) -> Unit
): ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        val plane = image.planes[0]
        val buffer = plane.buffer

        val avgGreen = calculateAverageGreen(
            buffer,
            image.width,
            image.height,
            plane.pixelStride,
            plane.rowStride
        )

        onGreenChannelAverage(avgGreen)
        image.close()
    }

    /**
     * Extracts and averages the Green channel from RGBA_8888 buffer.
     * In RGBA_8888, the bytes are ordered: R, G, B, A.
     */
    private fun calculateAverageGreen(
        buffer: ByteBuffer,
        width: Int,
        height: Int,
        pixelStride: Int,
        rowStride: Int
    ): Double {
        var sumGreen = 0L
        var count = 0

        // Sampling every 4th pixel horizontally and vertically to optimize performance
        // while maintaining signal integrity for rPPG.
        for(y in 0 until height step 4) {
            for (x in 0 until width step 4) {
                val index = (y * rowStride) + (x * pixelStride)
                if (index + 1 < buffer.capacity()) {
                    val g = buffer.get(index + 1).toInt() and 0xFF
                    sumGreen += g
                    count++
                }
            }
        }
        return if (count > 0) sumGreen.toDouble() / count else 0.0
    }
}