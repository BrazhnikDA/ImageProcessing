package com.harman.imageprocessingmvvm.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.harman.imageprocessingmvvm.data.ImageFilter

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
    suspend fun getImageFiltersBlur(image: Bitmap): List<ImageFilter>
    suspend fun saveFilteredImage(filteredBitmap: Bitmap): Uri?
}