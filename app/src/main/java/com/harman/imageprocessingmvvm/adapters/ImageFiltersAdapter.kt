package com.harman.imageprocessingmvvm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.harman.imageprocessingmvvm.R
import com.harman.imageprocessingmvvm.activities.editimage.authorization.LoginActivity
import com.harman.imageprocessingmvvm.data.ImageFilter
import com.harman.imageprocessingmvvm.databinding.ItemContainerFilterBinding
import com.harman.imageprocessingmvvm.listeners.ImageFilterListener
import org.koin.dsl.koinApplication
import java.util.*
import kotlin.coroutines.coroutineContext

class ImageFiltersAdapter(
    private val imageFilters: List<ImageFilter>,
    private val imageFilterListener: ImageFilterListener
) : RecyclerView.Adapter<ImageFiltersAdapter.ImageFilterViewHolder>() {

    private lateinit var prefs: SharedPreferences

    private var selectedFilterPosition = 0
    private var previouslySelectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFilterViewHolder {
        val binding = ItemContainerFilterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        prefs = parent.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return ImageFilterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ImageFilterViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val textSize: String = prefs
            .getString("font_size", "")
            .toString()

        val textColor: String = prefs
            .getString("font_color", "")
            .toString()


        var selectColor: Int = R.color.selectedFilter
        var primaryColor: Int = R.color.primaryText

        if (textColor == "Green")
            selectColor = R.color.selectedFilter
        if (textColor == "Blue")
            selectColor = Color.BLUE
        if (textColor == "Red")
            selectColor = Color.RED

        with(holder) {
            with(imageFilters[position]) {
                binding.imageFilterPreview.setImageBitmap(filterPreview)
                binding.textFilterName.text = name
                binding.root.setOnClickListener {
                    if (position != selectedFilterPosition) {
                        imageFilterListener.onFilterSelected(this)
                        previouslySelectedPosition = selectedFilterPosition
                        selectedFilterPosition = position
                        with(this@ImageFiltersAdapter) {
                            notifyItemChanged(previouslySelectedPosition, Unit)
                            notifyItemChanged(selectedFilterPosition, Unit)
                        }
                    }

                }
            }

            if(textSize != "")
                binding.textFilterName.textSize = textSize.toInt().toFloat()

            if (selectedFilterPosition == position) {
                binding.textFilterName.setTextColor(selectColor)
            } else {
                binding.textFilterName.setTextColor(primaryColor.toInt())
            }

            binding.imageFilterPreview.setBackgroundColor(
                ContextCompat.getColor(
                    binding.textFilterName.context,
                    if (selectedFilterPosition == position) {
                        R.color.black
                    } else {
                        R.color.white
                    }
                )
            )
        }
    }

    override fun getItemCount() = imageFilters.size

    inner class ImageFilterViewHolder(val binding: ItemContainerFilterBinding) :
        RecyclerView.ViewHolder(binding.root)
}