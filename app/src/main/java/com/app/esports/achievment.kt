package com.app.esports

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class achievment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievment)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val achievementListTextView = findViewById<TextView>(R.id.achievement_list)

        val years = StaticData.achievements.keys.toTypedArray()
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, years)

        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedYear = years[position]
            val achievements = StaticData.achievements[selectedYear]
            
            achievementListTextView.text = achievements?.joinToString("\n") ?: "No achievements found for $selectedYear"
        }
    }
}
