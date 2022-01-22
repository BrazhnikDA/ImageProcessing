package com.harman.imageprocessingmvvm.listeners

import com.harman.imageprocessingmvvm.data.ImageFilter

interface ImageFilterListener {
    fun onFilterSelected(imageFilter: ImageFilter)
}