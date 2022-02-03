package com.harman.imageprocessingmvvm.data

import android.graphics.Bitmap
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

/**
 * @param name Filter Title
 * @param filter Class object
 * @param filterPreview Image after applying the filter
 */
data class ImageFilter(
    val name: String,
    val filter: GPUImageFilter,
    val filterPreview: Bitmap
)