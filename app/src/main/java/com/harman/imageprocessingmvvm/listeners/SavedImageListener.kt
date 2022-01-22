package com.harman.imageprocessingmvvm.listeners

import java.io.File

interface SavedImageListener {
    fun onImageClicked(file: File)
}