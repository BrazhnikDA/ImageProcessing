package com.harman.imageprocessingmvvm

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.harman.imageprocessingmvvm.activities.main.MainActivity
import com.harman.imageprocessingmvvm.utilities.displayToast


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setListener()
    }

    private fun setListener() {
        val btnRegister: Button = findViewById(R.id.btn_register)
        val textViewLogin: TextView = findViewById(R.id.textView_login)

        val email: EditText = findViewById(R.id.et_register_email)
        val password: EditText = findViewById(R.id.et_register_password)

        btnRegister.setOnClickListener {
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
                        .createUserWithEmailAndPassword(emailString, passwordString)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val fireBaseUser: FirebaseUser = task.result!!.user!!
                                displayToast("You are registered successfully.")

                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", fireBaseUser.uid)
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

        textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}