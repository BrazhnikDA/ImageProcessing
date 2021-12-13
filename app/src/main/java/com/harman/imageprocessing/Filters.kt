package com.harman.imageprocessing

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.set


/*
abstract class Filters {
    // Для изменения отрисовки пикселя полученного из фильтра
    private var iRes = -1
    private var jRes = -1
    protected abstract fun calculateNewPixelColor(sourceImage: Bitmap, x: Int, y: Int): Color


    open fun processImage(sourceImage: Bitmap): Bitmap {
        if (sourceImage == null) {
            return sourceImage
        }
        for (i in 0 until sourceImage.width) {
            for (j in 0 until sourceImage.height) {
                if (iRes == -1) {
                    val color = calculateNewPixelColor(sourceImage, i, j)
                    sourceImage[i, j] = color

                } else {
                    val color = calculateNewPixelColor(sourceImage, i, j)
                    sourceImage[iRes, jRes] = color
                }
            }
        }
        return sourceImage
    }

    open fun clamp(value: Int, min: Int, max: Int): Int {
        if (value < min) return min
        return if (value > max) max else value
    }
}*/
