package com.harman.imageprocessingmvvm.activities.editimage.menu

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.harman.imageprocessingmvvm.R
import com.harman.imageprocessingmvvm.activities.editimage.EditImageActivity
import com.harman.imageprocessingmvvm.activities.editimage.authorization.LoginActivity
import com.harman.imageprocessingmvvm.utilities.displayToast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var prefs: SharedPreferences

    val APP_PREFERENCES_FONT_SIZE = "font_size"
    val APP_PREFERENCES_FONT_COLOR = "font_color"
    val APP_PREFERENCES_MAIN_THEME = "main_theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnApply: Button = view.findViewById(R.id.applySettings)
        prefs = this.activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)

        val chooseText: Array<out String> = resources.getStringArray(R.array.fontSize)
        val chooseColor: Array<out String> = resources.getStringArray(R.array.colorSetFilter)
        val chooseTheme: Array<out String> = resources.getStringArray(R.array.appTheme)

        val spinnerFont: Spinner = view.findViewById(R.id.spinnerFontSize)
        val spinnerColor: Spinner = view.findViewById(R.id.spinnerColorFont)
        val spinnerTheme: Spinner = view.findViewById(R.id.spinnerAppTheme)

        val textSize: String = prefs
            .getString("font_size", "")
            .toString()

        val textColor: String = prefs
            .getString("font_color", "")
            .toString()

        val mainTheme: String = prefs
            .getString("main_theme", "")
            .toString()

        spinnerFont.setSelection(chooseText.binarySearch(textSize))
        spinnerColor.setSelection(chooseColor.binarySearch(textColor))
        spinnerTheme.setSelection(chooseTheme.binarySearch(mainTheme))

        btnApply.setOnClickListener {
            val selectText: String = chooseText[spinnerFont.selectedItemPosition]
            val selectColor: String = chooseColor[spinnerColor.selectedItemPosition]
            val selectTheme: String = chooseTheme[spinnerTheme.selectedItemPosition]

            // Storing settings
            val editor = prefs.edit()
            editor.putString(APP_PREFERENCES_FONT_SIZE, selectText).apply()
            editor.putString(APP_PREFERENCES_FONT_COLOR, selectColor).apply()
            editor.putString(APP_PREFERENCES_MAIN_THEME, selectTheme).apply()

            context!!.displayToast("Settings applied!")
            //context!!.displayToast("$selectText $selectColor $selectTheme")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}