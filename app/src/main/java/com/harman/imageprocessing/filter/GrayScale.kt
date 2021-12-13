/*
package com.harman.imageprocessing.filter

import android.R.attr
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.harman.imageprocessing.Filters


class GrayScale : Filters() {
    override fun calculateNewPixelColor(sourceImage: Bitmap, x: Int, y: Int): Color {
        val sourceColor: Int = sourceImage.getPixel(attr.x, attr.y) ?: 0
        val intensity = (
                0.299 * sourceColor.red + 0.587 *
                        sourceColor.green + 0.114 *
                        sourceColor.blue)

        return Color(red = intensity, green = intensity, blue = intensity, alpha = 0xFF)
    }
}*/
