package com.harman.imageprocessingmvvm.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File

class SavedImagesRepositoryImpl(private val context: Context): SavedImagesRepository {

    private var countImageSaved: Int = 0

    override suspend fun loadSavedImages(): List<Pair<File, Bitmap>>? {
        val savedImages = ArrayList<Pair<File, Bitmap>>()
        val dir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "Saved Images"
        )
        dir.listFiles()?.let { data ->
            data.forEach { file ->
                savedImages.add(Pair(file, getPreviewBitmap(file)))
            }
            countImageSaved = savedImages.size
            return savedImages
        } ?: run {
            countImageSaved = 0
            return null
        }
    }

    override fun getCountImage(): Int {
        countImageSaved?.let {
            return countImageSaved
        } ?: return 0
    }

    private fun getPreviewBitmap(file: File): Bitmap {
        val originalBitmap = BitmapFactory.decodeFile(file.absolutePath)
        val width = 150
        val height = ((originalBitmap.height * width) / originalBitmap.width)
        return Bitmap.createScaledBitmap(originalBitmap, width, height, false)
    }
}