package com.harman.imageprocessingmvvm.activities.editimage.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
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
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var prefs: SharedPreferences

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = this.activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)

        val tvEmail: TextView = view.findViewById(R.id.emailUser)
        tvEmail.text = "Email: " + prefs.getString("email", "").toString()

        val btnLogout: Button = view.findViewById(R.id.btn_logout_profile)

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val editor = prefs.edit()
            editor.remove(EditImageActivity.APP_PREFERENCES_EMAIL).apply()
            editor.remove(EditImageActivity.APP_PREFERENCES_PASSWORD).apply()

            context!!.displayToast("Logout")
            val intent = Intent (activity, LoginActivity::class.java)
            activity!!.startActivity(intent)
            this.activity!!.finish()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}