package com.example.zd2_v4_karamov

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zd2_v4_karamov.databinding.ActivityLoginBinding

const val PREFS = "LoginPrefs"
const val KEY_EMAIL = "Email"
const val  KEY_PASSWORD = "Password"

class Login : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE)

        val savedEmail = sharedPreferences.getString(KEY_EMAIL, "")
        val savedPassword = sharedPreferences.getString(KEY_PASSWORD, "")

        binding.emailText.setText(savedEmail)
        binding.passwordText.setText(savedPassword)

        binding.buttonEnter.setOnClickListener {
            if (!binding.emailText.text.toString().isNullOrEmpty() && !binding.passwordText.text.toString().isNullOrEmpty())
            {

                sharedPreferences.edit {
                    putString(KEY_EMAIL, binding.emailText.text.toString())
                    putString(KEY_PASSWORD, binding.passwordText.text.toString())
                    apply()
                }

                val intent = Intent(this@Login, Main::class.java)
                startActivity(intent)
                finish()

            }
            else
            {
                Toast.makeText(
                    this,
                    "Неверный ввод почты или пароля",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}