package com.app.esports.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.esports.SignInFragment
import com.app.esports.databinding.ActivityAuthBinding

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