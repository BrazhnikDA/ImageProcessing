package com.harman.imageprocessingmvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.harman.imageprocessingmvvm.databinding.ActivityEditImageBinding
import com.harman.imageprocessingmvvm.utilities.displayToast

class SendOTPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_o_t_p)

        val inputMobile: EditText = this.findViewById(R.id.inputMobile)
        val buttonGetOTP: Button = this.findViewById(R.id.buttonGetOTP)

        buttonGetOTP.setOnClickListener {
            if (inputMobile.text.toString().trim().isEmpty()) {
                this.displayToast("Enter Mobile")
                return@setOnClickListener
            }
            var intent = Intent(this, VerifyOTPActivity::class.java)
            intent.putExtra("mobile", inputMobile.text.toString())
            startActivity(intent)
        }

    }
}