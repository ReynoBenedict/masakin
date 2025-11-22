package com.example.masakin.ui.mart.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Custom Filter Icon (Funnel shape)
 * Dibuat manual pakai Canvas untuk ukuran lebih besar
 */
@Composable
fun CustomFilterIcon(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    color: Color = Color(0xFF374151),
    strokeWidth: Dp = 2.5.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val width = this.size.width
        val height = this.size.height

        val path = Path().apply {
            // Top line (wide part of funnel)
            moveTo(width * 0.1f, height * 0.15f)
            lineTo(width * 0.9f, height * 0.15f)

            // Left diagonal line
            lineTo(width * 0.4f, height * 0.55f)

            // Left vertical line (narrow part)
            lineTo(width * 0.4f, height * 0.85f)

            // Bottom line
            lineTo(width * 0.6f, height * 0.85f)

            // Right vertical line
            lineTo(width * 0.6f, height * 0.55f)

            // Right diagonal line
            lineTo(width * 0.9f, height * 0.15f)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // Add horizontal lines for "filter" effect
        drawLine(
            color = color,
            start = Offset(width * 0.25f, height * 0.35f),
            end = Offset(width * 0.75f, height * 0.35f),
            strokeWidth = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )
    }
}