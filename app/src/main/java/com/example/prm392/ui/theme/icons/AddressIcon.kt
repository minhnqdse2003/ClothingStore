package com.example.prm392.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val AddressIcon: ImageVector
    get() {
        if (_Location != null) {
            return _Location!!
        }
        _Location = ImageVector.Builder(
            name = "Location",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(10.832f, 2.688f)
                arcTo(4.056f, 4.056f, 0f, isMoreThanHalf = false, isPositiveArc = false, 8.02f, 1.5f)
                horizontalLineToRelative(-0.04f)
                arcToRelative(4.056f, 4.056f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4f, 4f)
                curveToRelative(-0.013f, 0.75f, 0.198f, 1.487f, 0.606f, 2.117f)
                lineTo(7.734f, 14f)
                horizontalLineToRelative(0.533f)
                lineToRelative(3.147f, -6.383f)
                curveToRelative(0.409f, -0.63f, 0.62f, -1.367f, 0.606f, -2.117f)
                arcToRelative(4.056f, 4.056f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.188f, -2.812f)
                close()
                moveTo(7.925f, 2.5f)
                lineToRelative(0.082f, 0.01f)
                lineToRelative(0.074f, -0.01f)
                arcToRelative(3.075f, 3.075f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.941f, 3.037f)
                arcToRelative(2.74f, 2.74f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.467f, 1.568f)
                lineToRelative(-0.02f, 0.034f)
                lineToRelative(-0.017f, 0.035f)
                lineTo(8f, 12.279f)
                lineToRelative(-2.517f, -5.1f)
                lineToRelative(-0.017f, -0.039f)
                lineToRelative(-0.02f, -0.034f)
                arcToRelative(2.74f, 2.74f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.467f, -1.568f)
                arcTo(3.074f, 3.074f, 0f, isMoreThanHalf = false, isPositiveArc = true, 7.924f, 2.5f)
                close()
                moveToRelative(0.612f, 2.169f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = false, -1.112f, 1.663f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.112f, -1.663f)
                close()
                moveTo(6.87f, 3.837f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, 2.22f, 3.326f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.22f, -3.326f)
                close()
            }
        }.build()
        return _Location!!
    }

private var _Location: ImageVector? = null
