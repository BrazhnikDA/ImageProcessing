package com.harman.imageprocessingmvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.harman.imageprocessingmvvm.activities.main.MainActivity
import com.harman.imageprocessingmvvm.utilities.displayToast
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

class VerifyOTPActivity : AppCompatActivity() {

    private lateinit var inputCode1: EditText
    private lateinit var inputCode2: EditText
    private lateinit var inputCode3: EditText
    private lateinit var inputCode4: EditText
    private lateinit var inputCode5: EditText
    private lateinit var inputCode6: EditText

    private lateinit var verificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_o_t_p)

        val textName: String = {
            (intent.getStringExtra("name")).toString()
        }.toString()

        val textMobile: TextView = findViewById(R.id.textMobile)
        textMobile.text = String.format(
            "+7-%s", intent.getStringExtra("mobile")
        )

        inputCode1 = this.findViewById(R.id.inputCode1)
        inputCode2 = this.findViewById(R.id.inputCode2)
        inputCode3 = this.findViewById(R.id.inputCode3)
        inputCode4 = this.findViewById(R.id.inputCode4)
        inputCode5 = this.findViewById(R.id.inputCode5)
        inputCode6 = this.findViewById(R.id.inputCode6)

        var button: Button = this.findViewById(R.id.buttonVerify)
        button.setOnClickListener {

        }

        setupOTPInputs()

        var progressBar: ProgressBar = findViewById(R.id.progressBar)
        var buttonVerify: Button = findViewById(R.id.buttonVerify)

        verificationId = intent.getStringExtra("verificationId").toString()

        buttonVerify.setOnClickListener {
            if (inputCode1.text.toString().trim().isEmpty()
                || inputCode2.text.toString().trim().isEmpty()
                || inputCode3.text.toString().trim().isEmpty()
                || inputCode4.text.toString().trim().isEmpty()
                || inputCode5.text.toString().trim().isEmpty()
                || inputCode6.text.toString().trim().isEmpty()
            ) {

                displayToast("Please enter valid code")
                return@setOnClickListener
            }
            var code = StringBuilder()
            code.append(inputCode1.text.toString())
            code.append(inputCode2.text.toString())
            code.append(inputCode3.text.toString())
            code.append(inputCode4.text.toString())
            code.append(inputCode5.text.toString())
            code.append(inputCode6.text.toString())
            if (verificationId.isNotEmpty()) {
                View.VISIBLE.also { progressBar.visibility = it }
                View.INVISIBLE.also { buttonVerify.visibility = it }

                var phoneAuthCredential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    verificationId,
                    code.toString()
                )

                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                    .addOnCompleteListener {
                        View.GONE.also { progressBar.visibility = it }
                        View.VISIBLE.also { buttonVerify.visibility = it }
                        if (it.isSuccessful) {
                            var intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK xor
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("name", textName)

                            startActivity(intent)
                        } else {
                            displayToast("The verification code entered was invalid")
                        }
                    }
            }
        }
        var textView: TextView = findViewById(R.id.textResendOTP)
        textView.setOnClickListener {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+7" + intent.getStringExtra("mobile"),
                60,
                TimeUnit.SECONDS,
                this,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        displayToast(p0.message)
                    }

                    override fun onCodeSent(
                        newVerificationId: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        verificationId = newVerificationId
                        displayToast("OTP Sent")
                    }
                }
            )
        }
    }

    private fun setupOTPInputs() {
        inputCode1.addTextChangedListener {
            if (inputCode1.text.trim().isNotEmpty()) {
                inputCode2.requestFocus()
            }
        }

        inputCode2.addTextChangedListener {
            if (inputCode2.text.trim().isNotEmpty()) {
                inputCode3.requestFocus()
            }
        }

        inputCode3.addTextChangedListener {
            if (inputCode3.text.trim().isNotEmpty()) {
                inputCode4.requestFocus()
            }
        }

        inputCode4.addTextChangedListener {
            if (inputCode4.text.trim().isNotEmpty()) {
                inputCode5.requestFocus()
            }
        }

        inputCode5.addTextChangedListener {
            if (inputCode5.text.trim().isNotEmpty()) {
                inputCode6.requestFocus()
            }
        }

        inputCode6.addTextChangedListener {
            if (inputCode6.text.trim().isNotEmpty()) {
                val inputMethodManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            }
        }
    }
}