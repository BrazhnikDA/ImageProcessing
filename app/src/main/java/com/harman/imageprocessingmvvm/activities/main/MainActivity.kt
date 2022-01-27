package com.harman.imageprocessingmvvm.activities.main

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.harman.imageprocessingmvvm.activities.editimage.EditImageActivity
import com.harman.imageprocessingmvvm.activities.savedimages.SavedImagesActivity
import com.harman.imageprocessingmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // To take an image
    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1
        const val KEY_IMAGE_URI = "imageUri"
    }

    // Name user
    private var textName: String = ""

    // Bind the activity screen "activity_main"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textName = {
            (intent.getStringExtra("name")).toString()
        }.toString()

        setListeners()
    }

    // Listener of button clicks
    private fun setListeners() {
        // Take new image in gallery
        binding.buttonEditNewImage.setOnClickListener() {
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ).also { pickerIntent ->
                pickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(pickerIntent, REQUEST_CODE_PICK_IMAGE)
            }
        }
        // Viewing saved images
        binding.buttonViewSavedImages.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    SavedImagesActivity::class.java
                )
            )
        }
    }

    // Transmitting the image address between activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            data?.data?.let { imageUri ->
                Intent(
                    applicationContext,
                    EditImageActivity::class.java
                ).also { editImageIntent ->
                    editImageIntent.putExtra(KEY_IMAGE_URI, imageUri)
                    editImageIntent.putExtra("name", textName)
                    startActivity(editImageIntent)
                }
            }
        }
    }

}