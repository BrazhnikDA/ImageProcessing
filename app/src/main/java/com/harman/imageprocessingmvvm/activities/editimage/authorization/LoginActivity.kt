package com.harman.imageprocessingmvvm.activities.editimage.authorization

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.harman.imageprocessingmvvm.R
import com.harman.imageprocessingmvvm.activities.main.MainActivity
import com.harman.imageprocessingmvvm.utilities.displayToast

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    companion object {
        const val APP_PREFERENCES_EMAIL = "email"
        const val APP_PREFERENCES_PASSWORD = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        autoLogin()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setListener()
    }

     private fun autoLogin() {
        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)

        if (prefs.contains(APP_PREFERENCES_EMAIL) && prefs.contains(APP_PREFERENCES_PASSWORD)) {
            // Get email, password
            val email: String = prefs.getString(APP_PREFERENCES_EMAIL, "").toString()
            val password: String = prefs.getString(APP_PREFERENCES_PASSWORD, "").toString()

            if(email == "" || password == "")
                return

            // Login
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", task.result!!.user!!.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()
                    }
                }
        }
    }

    private fun setListener() {
        val btnLogin: Button = findViewById(R.id.btn_login)
        val textRegister: TextView = findViewById(R.id.textView_register)

        val email: EditText = findViewById(R.id.et_login_email)
        val password: EditText = findViewById(R.id.et_login_password)

        btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                    displayToast("Please Enter Email")
                }
                TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                    displayToast("Please Enter Password")
                }
                else -> {
                    val emailString: String = email.text.toString().trim { it <= ' ' }
                    val passwordString: String = password.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(emailString, passwordString)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                displayToast("You are logged in successfully.")

                                // Storing data
                                val editor = prefs.edit()
                                editor.putString(APP_PREFERENCES_EMAIL, emailString).apply()
                                editor.putString(APP_PREFERENCES_PASSWORD, passwordString).apply()

                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("email_id", emailString)
                                startActivity(intent)
                                finish()
                            } else {
                                displayToast(task.exception!!.message.toString())
                            }
                        }
                }
            }
        }
        textRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}