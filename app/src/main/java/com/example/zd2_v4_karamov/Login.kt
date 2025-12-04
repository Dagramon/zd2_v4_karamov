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
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

data class User(
    var email: String,
    var password: String,
)

const val PREFS = "LoginPrefs"
const val KEY_USER_JSON = "User"

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

        val jsonString = sharedPreferences.getString(KEY_USER_JSON, "")

        var user = Gson().fromJson(jsonString, User::class.java)

        if (user != null)
        {
            binding.emailText.setText(user.email)
            binding.passwordText.setText(user.password)
        }
        else
        {
            user = User("", "")
        }

        binding.buttonEnter.setOnClickListener {
            if (!binding.emailText.text.toString().isNullOrEmpty() && !binding.passwordText.text.toString().isNullOrEmpty())
            {
                user.email = binding.emailText.text.toString()
                user.password = binding.passwordText.text.toString()
                var jsonUser = Gson().toJson(user)
                sharedPreferences.edit {
                    putString(KEY_USER_JSON, jsonUser)
                    apply()
                }

                val intent = Intent(this@Login, Main::class.java)
                startActivity(intent)
                finish()

            }
            else
            {
                Snackbar.make(binding.emailText, "Неверный ввод почты или пароля", Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}