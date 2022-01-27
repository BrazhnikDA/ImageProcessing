package com.harman.imageprocessingmvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.harman.imageprocessingmvvm.utilities.displayToast
import java.util.concurrent.TimeUnit

class SendOTPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_o_t_p)

        val inputName: EditText = findViewById(R.id.inputName)
        val inputMobile: EditText = findViewById(R.id.inputMobile)
        val buttonGetOTP: Button = findViewById(R.id.buttonGetOTP)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        inputMobile.addTextChangedListener {
            if(inputMobile.text.trim().length == 10) {
                val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            }
        }

        buttonGetOTP.setOnClickListener {
            if(inputName.text.toString().trim().isEmpty()) {
                this.displayToast("Enter Your Name")
                return@setOnClickListener
            }

            if (inputMobile.text.toString().trim().isEmpty()) {
                this.displayToast("Enter Mobile")
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            buttonGetOTP.visibility = View.INVISIBLE

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+7" + inputMobile.text.toString(),
                60,
                TimeUnit.SECONDS,
                this,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        View.GONE.also { progressBar.visibility = it }
                        View.VISIBLE.also { buttonGetOTP.visibility = it }
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        View.GONE.also { progressBar.visibility = it }
                        View.VISIBLE.also { buttonGetOTP.visibility = it }
                        displayToast(p0.message)
                    }

                    override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        View.GONE.also { progressBar.visibility = it }
                        View.VISIBLE.also { progressBar.visibility = it }
                        with(Intent(this@SendOTPActivity, VerifyOTPActivity::class.java)) {
                            putExtra("name", inputName.text.toString())
                            putExtra("mobile", inputMobile.text.toString())
                            putExtra("verificationId", p0)
                            startActivity(this)
                        }
                    }
                }
            )

            val intent = Intent(this, VerifyOTPActivity::class.java)
            intent.putExtra("mobile", inputMobile.text.toString())
            startActivity(intent)
        }

    }
}