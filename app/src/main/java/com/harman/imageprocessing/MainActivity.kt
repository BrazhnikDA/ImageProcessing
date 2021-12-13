package com.harman.imageprocessing

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import java.io.IOException


const val GALLERY_REQUEST = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openImageMedia()

    }

    private fun openImageMedia() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        this.startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var bitmap: Bitmap? = null
        val image: SubsamplingScaleImageView = findViewById(R.id.viewImage)

        //Log.d("OPEN_IMAGE: ", requestCode.toString())
        when(requestCode) {
            GALLERY_REQUEST -> {
                if (resultCode === RESULT_OK) {
                    val selectedImage: Uri? = data?.data
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    image.setImage(ImageSource.bitmap(bitmap!!))
                }
            }
        }
        Log.d("OPEN_IMAGE_Status: ", bitmap.toString())
        if(bitmap == null)
            openImageMedia()
    }
}