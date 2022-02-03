package com.harman.imageprocessingmvvm.activities.savedimages

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.harman.imageprocessingmvvm.activities.editimage.EditImageActivity
import com.harman.imageprocessingmvvm.activities.filteredimage.FilteredImageActivity
import com.harman.imageprocessingmvvm.adapters.SavedImagesAdapter
import com.harman.imageprocessingmvvm.databinding.ActivitySavedImagesBinding
import com.harman.imageprocessingmvvm.listeners.SavedImageListener
import com.harman.imageprocessingmvvm.utilities.displayToast
import com.harman.imageprocessingmvvm.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SavedImagesActivity : AppCompatActivity(), SavedImageListener {

    private lateinit var binding: ActivitySavedImagesBinding
    private val viewModel: SavedImagesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setListeners()
        viewModel.loadSavedImages()
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserver() {
        viewModel.savedImagesUiState.observe(this, {
            val savedImagesDataState = it ?: return@observe
            binding.savedImagesProgressBar.visibility =
                if (savedImagesDataState.isLoading) View.VISIBLE else View.GONE
            savedImagesDataState.savedImages?.let { savedImages ->
                SavedImagesAdapter(savedImages, this).also { adapter ->
                    with(binding.savedImagesRecyclerView) {
                        this.adapter = adapter
                        visibility = View.VISIBLE
                    }
                    binding.countImage.text = "Count Images: " + viewModel.getCountImage().toString()
                }
            } ?: kotlin.run {
                savedImagesDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onImageClicked(file: File) {
        val fileUri = FileProvider.getUriForFile(
            applicationContext,
            "${packageName}.provider",
            file
        )
        Intent(
            applicationContext,
            FilteredImageActivity::class.java
        ).also { filteredImageIntent ->
            filteredImageIntent.putExtra(EditImageActivity.KEY_FILTERED_IMAGE_URI, fileUri)
            startActivity(filteredImageIntent)
        }
    }
}