package com.harman.imageprocessingmvvm.utilities

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * Display message
 */
fun Context.displayToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Changing visibility
 */
fun View.show() {
    this.visibility = View.VISIBLE
}