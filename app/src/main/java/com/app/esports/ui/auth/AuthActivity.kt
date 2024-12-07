package com.app.esports.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.MainActivity
import com.app.esports.SignInFragment
import com.app.esports.SignInFragment.Companion.REMEMBER_ME
import com.app.esports.SignInFragment.Companion.SAVED_PASSWORD
import com.app.esports.SignInFragment.Companion.SAVED_USERNAME
import com.app.esports.User
import com.app.esports.databinding.ActivityAuthBinding
import org.json.JSONObject

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        supportFragmentManager.beginTransaction().apply {
            add(binding.main.id, SignInFragment())
            commit()
        }
    }


}