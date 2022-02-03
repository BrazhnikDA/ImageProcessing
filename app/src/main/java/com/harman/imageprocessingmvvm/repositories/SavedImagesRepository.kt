package com.harman.imageprocessingmvvm.repositories

import android.graphics.Bitmap
import java.io.File

interface SavedImagesRepository {
    suspend fun loadSavedImages(): List<Pair<File, Bitmap>>?
    fun getCountImage(): Int
}