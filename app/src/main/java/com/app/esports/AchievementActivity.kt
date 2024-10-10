package com.app.esports

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.LeadingMarginSpan
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.esports.databinding.ActivityAchievmentBinding
import com.game.esports.GameAdapter

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAchievmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val game = intent.getStringExtra(GameAdapter.GAME)
        val img = intent.getIntExtra(GameAdapter.IMG, R.drawable.valorant)

        binding.heading.setText(game)
        binding.imgGame.setBackgroundResource(img)

        val years = arrayOf("All", "2018", "2019", "2020", "2021", "2022", "2023", "2024")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapter

        val achievementListTextView = findViewById<TextView>(R.id.achievement_list)

        // Correct implementation of OnItemSelectedListener
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedYear = parent.getItemAtPosition(position).toString()

                val achievements = if (selectedYear == "All") {
                    StaticData.achievements[game]?.flatMap { it.value.toList()}?.toTypedArray()
                } else {
                    StaticData.achievements[game]?.get(selectedYear)
                }

                if (achievements==null) return

                val orderedList = achievements.mapIndexed { index, achievement ->
                    "${index+1}. $achievement"
                }?.joinToString("\n")

                val spannableString = SpannableString(orderedList)

                // Apply leading margin (indent) for wrapped lines after the first line
                val lines = orderedList?.lines()
                lines?.forEachIndexed { index, line ->
                    val startIndex = orderedList.indexOf(line)
                    val endIndex = startIndex+line.length

                    // Get the number part (e.g., "1.", "2.", etc.)
                    val numberPart = line.substringBefore(". ") + "."

                    // Measure the width of the number part
                    val textPaint = binding.heading.paint
                    val numberWidth = textPaint.measureText(numberPart).toInt()

                    // Apply LeadingMarginSpan with dynamic margin based on the width of the number part
                    spannableString.setSpan(
                        LeadingMarginSpan.Standard(0, numberWidth), // First line's margin is the number's width
                        startIndex,
                        endIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                achievementListTextView.text = spannableString
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case when no year is selected if needed
            }
        }
    }
    val spannableString = SpannableStringBuilder()
    fun addListItem(number: Int, text: String) {
        val item = "$number. $text"
        spannableString.append(item)
        // Add indentation for any line that wraps to the next
        val bulletSpan = LeadingMarginSpan.Standard(50, 50)
        spannableString.setSpan(bulletSpan, spannableString.length - item.length, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.append("\n")
    }
}
