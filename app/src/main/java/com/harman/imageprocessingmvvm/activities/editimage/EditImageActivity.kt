package com.harman.imageprocessingmvvm.activities.editimage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.harman.imageprocessingmvvm.activities.filteredimage.FilteredImageActivity
import com.harman.imageprocessingmvvm.activities.main.MainActivity
import com.harman.imageprocessingmvvm.adapters.ImageFiltersAdapter
import com.harman.imageprocessingmvvm.data.ImageFilter
import com.harman.imageprocessingmvvm.databinding.ActivityEditImageBinding
import com.harman.imageprocessingmvvm.listeners.ImageFilterListener
import com.harman.imageprocessingmvvm.utilities.displayToast
import com.harman.imageprocessingmvvm.utilities.show
import com.harman.imageprocessingmvvm.viewmodels.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditImageActivity : AppCompatActivity(), ImageFilterListener {

    companion object {
        const val KEY_FILTERED_IMAGE_URI = "filtered"
    }

    // User
    private var userId: String = ""
    private var userEmail: String = ""

    private lateinit var binding: ActivityEditImageBinding
    private val viewModel: EditImageViewModel by viewModel()
    private lateinit var gpuImage: GPUImage

    // Image bitmaps
    private lateinit var originalBitmap: Bitmap
    private val filteredBitmap = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setupObservers()
        setupObserversBlur()

        prepareImagePreview()
        binding.filterRecyclerView.visibility = View.GONE
        binding.filterBlurRecyclerView.visibility = View.GONE

        // Get user
        userId = intent.getStringExtra("user_id").toString()
        userEmail = intent.getStringExtra("email_id").toString()

        // Menu
        binding.imageMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navigationView.itemIconTintList = null
        val navController: NavController = Navigation.findNavController(this, com.harman.imageprocessingmvvm.R.id.navHostFragment)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
    }

    private fun setupObservers() {
        viewModel.imagePreviewUiState.observe(this, {
            val dataState = it ?: return@observe
            binding.previewProgressBar.visibility =
                if (dataState.isLoading) View.GONE else View.GONE
            dataState.bitmap?.let { bitmap ->
                // For the first time 'filtered image = original image'
                originalBitmap = bitmap
                filteredBitmap.value = bitmap

                with(originalBitmap) {
                    gpuImage.setImage(this)
                    binding.imagePreview.show()
                    viewModel.loadImageFilters(this)
                }
            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
        viewModel.imageFiltersUiState.observe(this, {
            val imageFiltersDataState = it ?: return@observe
            binding.imageFiltersProgressBar.visibility =
                if (imageFiltersDataState.isLoading) View.GONE else View.GONE
            imageFiltersDataState.imageFilters?.let { imageFilters ->
                ImageFiltersAdapter(imageFilters, this).also { adapter ->
                    binding.filterRecyclerView.adapter = adapter
                }
            } ?: kotlin.run {
                imageFiltersDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
        filteredBitmap.observe(this, { bitmap ->
            binding.imagePreview.setImageBitmap(bitmap)
        })
        /*viewModel.saveFilteredImageUiState.observe(this, {
            val saveFilteredImageDataState = it ?: return@observe
           *//* if (saveFilteredImageDataState.isLoading) {
                binding.imageSave.visibility = View.GONE
                binding.savingProgressBar.visibility = View.VISIBLE
            } else {
                binding.savingProgressBar.visibility = View.GONE
                binding.imageSave.visibility = View.VISIBLE
            }*//*
            saveFilteredImageDataState.uri?.let { savedImageUri ->
                Intent(
                    applicationContext,
                    FilteredImageActivity::class.java
                ).also { filteredImageIntent ->
                    filteredImageIntent.putExtra(KEY_FILTERED_IMAGE_URI, savedImageUri)
                    startActivity(filteredImageIntent)
                }
            } ?: kotlin.run {
                saveFilteredImageDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })*/
    }

    private fun setupObserversBlur() {
        viewModel.imagePreviewUiState.observe(this, {
            val dataState = it ?: return@observe
            binding.previewProgressBar.visibility =
                if (dataState.isLoading) View.GONE else View.GONE
            dataState.bitmap?.let { bitmap ->
                // For the first time 'filtered image = original image'
                originalBitmap = bitmap
                filteredBitmap.value = bitmap

                with(originalBitmap) {
                    gpuImage.setImage(this)
                    binding.imagePreview.show()
                    viewModel.loadImageFiltersBlur(this)
                }
            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
        viewModel.imageFiltersUiState.observe(this, {
            val imageFiltersDataState = it ?: return@observe
            binding.imageFiltersBlurProgressBar.visibility =
                if (imageFiltersDataState.isLoading) View.GONE else View.GONE
            imageFiltersDataState.imageFilters?.let { imageFilters ->
                ImageFiltersAdapter(imageFilters, this).also { adapter ->
                    binding.filterBlurRecyclerView.adapter = adapter
                }
            } ?: kotlin.run {
                imageFiltersDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
        filteredBitmap.observe(this, { bitmap ->
            //binding.imagePreview.setImageBitmap(bitmap)
            binding.imagePreview.setImageBitmap(bitmap)
        })
       /* viewModel.saveFilteredImageUiState.observe(this, {
            val saveFilteredImageDataState = it ?: return@observe
            *//*if (saveFilteredImageDataState.isLoading) {
                binding.imageSave.visibility = View.GONE
                binding.savingProgressBar.visibility = View.VISIBLE
            } else {
                binding.savingProgressBar.visibility = View.GONE
                binding.imageSave.visibility = View.VISIBLE
            }*//*
            saveFilteredImageDataState.uri?.let { savedImageUri ->
                Intent(
                    applicationContext,
                    FilteredImageActivity::class.java
                ).also { filteredImageIntent ->
                    filteredImageIntent.putExtra(KEY_FILTERED_IMAGE_URI, savedImageUri)
                    startActivity(filteredImageIntent)
                }
            } ?: kotlin.run {
                saveFilteredImageDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })*/
    }

    private fun prepareImagePreview() {
        gpuImage = GPUImage(applicationContext)
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            viewModel.prepareImagePreview(imageUri)
        }
    }

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
        binding.imageSave.setOnClickListener {
            filteredBitmap.value?.let { bitmap ->
                viewModel.saveFilteredImage(bitmap)
            }
        }

        binding.buttonFilter.setOnClickListener {
            if (binding.filterBlurRecyclerView.visibility == View.VISIBLE) {
                binding.filterBlurRecyclerView.visibility = View.GONE
                binding.buttonBlur.setTextColor(Color.WHITE)
            }

            if (binding.filterRecyclerView.visibility == View.GONE) {
                binding.filterRecyclerView.visibility = View.VISIBLE
                binding.buttonFilter.setTextColor(Color.BLACK)
                setupObservers()
            }
        }

        binding.buttonBlur.setOnClickListener {
            if (binding.filterRecyclerView.visibility == View.VISIBLE) {
                binding.filterRecyclerView.visibility = View.GONE
                binding.buttonFilter.setTextColor(Color.WHITE)
            }

            if (binding.filterBlurRecyclerView.visibility == View.GONE) {
                binding.filterBlurRecyclerView.visibility = View.VISIBLE
                binding.buttonBlur.setTextColor(Color.BLACK)
                setupObserversBlur()
            }
        }

        viewModel.saveFilteredImageUiState.observe(this, {
            val saveFilteredImageDataState = it ?: return@observe
            if (saveFilteredImageDataState.isLoading) {
            binding.imageSave.visibility = View.GONE
            binding.savingProgressBar.visibility = View.VISIBLE
        } else {
            binding.savingProgressBar.visibility = View.GONE
            binding.imageSave.visibility = View.VISIBLE
        }
        saveFilteredImageDataState.uri?.let { savedImageUri ->
            Intent(
                applicationContext,
                FilteredImageActivity::class.java
            ).also { filteredImageIntent ->
                filteredImageIntent.putExtra(KEY_FILTERED_IMAGE_URI, savedImageUri)
                filteredImageIntent.putExtra("draft", "Draft Saved!")
                startActivity(filteredImageIntent)
            }
        } ?: kotlin.run {
            saveFilteredImageDataState.error?.let { error ->
                displayToast(error)
            }
        }
    })

        /*
        This will show original image when we long click ImageView until we release click,
        So that we can see difference between original image and filtered image
         */
        binding.imagePreview.setOnLongClickListener {
            binding.imagePreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }
        binding.imagePreview.setOnClickListener {
            filteredBitmap.value?.let { value ->
                binding.imagePreview.setImageBitmap(value)
            }
        }
    }

    override fun onFilterSelected(imageFilter: ImageFilter) {
        with(imageFilter) {
            with(gpuImage) {
                setFilter(filter)
                filteredBitmap.value = bitmapWithFilterApplied
            }
        }
    }
}