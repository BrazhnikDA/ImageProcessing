package com.harman.imageprocessingmvvm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class VerifyOTPActivity : AppCompatActivity() {

    private lateinit var inputCode1 : EditText
    private lateinit var inputCode2 : EditText
    private lateinit var inputCode3 : EditText
    private lateinit var inputCode4 : EditText
    private lateinit var inputCode5 : EditText
    private lateinit var inputCode6 : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_o_t_p)

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
        button.setOnClickListener{

        }

        setupOTPInputs()
    }

    private fun setupOTPInputs() {
        inputCode1.addTextChangedListener {
            if(inputCode1.text.trim().isNotEmpty()) {
                inputCode2.requestFocus()
            }
        }

        inputCode2.addTextChangedListener {
            if(inputCode2.text.trim().isNotEmpty()) {
                inputCode3.requestFocus()
            }
        }

        inputCode3.addTextChangedListener {
            if(inputCode3.text.trim().isNotEmpty()) {
                inputCode4.requestFocus()
            }
        }

        inputCode4.addTextChangedListener {
            if(inputCode4.text.trim().isNotEmpty()) {
                inputCode5.requestFocus()
            }
        }

        inputCode5.addTextChangedListener {
            if(inputCode5.text.trim().isNotEmpty()) {
                inputCode6.requestFocus()
            }
        }

        inputCode6.addTextChangedListener {
            if(inputCode6.text.trim().isNotEmpty()) {

            }
        }
    }
}