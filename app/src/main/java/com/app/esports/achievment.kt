package com.app.esports

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class achievment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievment)

        val years = arrayOf("All", "2018", "2019", "2020", "2021", "2022", "2023", "2024")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

        val achievementListTextView = findViewById<TextView>(R.id.achievement_list)

        // Correct implementation of OnItemSelectedListener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedYear = parent.getItemAtPosition(position).toString()

                val achievements = if (selectedYear == "All") {
                    StaticData.achievements["Valorant"]?.flatMap { it.value.toList()}?.toTypedArray()
                } else {
                    StaticData.achievements["Valorant"]?.get(selectedYear)
                }

                achievementListTextView.text = achievements?.joinToString("\n") ?: "No achievements found for $selectedYear"
                val orderedList = achievements?.mapIndexed { index, achievement ->
                    "${index + 1}. $achievement"
                }?.joinToString("<br>")

                achievementListTextView.text = Html.fromHtml(orderedList.toString(), Html.FROM_HTML_MODE_LEGACY)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case when no year is selected if needed
            }
        }
    }
}
