package com.app.esports

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.LeadingMarginSpan
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.databinding.ActivityAchievmentBinding
import com.app.esports.ui.teams.Team
import com.game.esports.GameAdapter
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAchievmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val game = intent.getStringExtra(GameAdapter.GAME)
        val img = intent.getStringExtra(GameAdapter.IMG)

        binding.heading.setText(game)

        val builder = Picasso.Builder(this)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        val imgV = ImageView(this)
        builder.build().load(img).into(imgV, object : Callback {
            override fun onSuccess() {
                binding.imgGame.background = imgV.drawable
            }

            override fun onError(e: Exception?) {
                Log.e("picasso", e?.message.toString())
            }
        })

        val years = arrayOf("All", "2018", "2019", "2020", "2021", "2022", "2023", "2024")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapter

        val achievementListTextView = findViewById<TextView>(R.id.achievement_list)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedYear = parent.getItemAtPosition(position).toString()

//                val achievements = if (selectedYear == "All") {
//                    StaticData.achievements[game]?.flatMap { it.value.toList()}?.toTypedArray()
//                } else {
//                    StaticData.achievements[game]?.get(selectedYear)
//                }

                val q = Volley.newRequestQueue(this@AchievementActivity)
                val url = "https://ubaya.xyz/native/160922001/api/get_achievements.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    {
                        Log.d("apiresult", it)
                        val obj = JSONObject(it)
                        if (obj.getString("result") == "OK"){
                            val sType = object : TypeToken<List<Achievement>>() { }.type
                            val achievements = Gson().fromJson<List<Achievement>>(obj.getString("data"), sType)
                            loadAchievements(achievements)
                        }
                    },
                    {
                        Log.e("apiresult", it.message.toString())
                    },
                ){
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["year"] = selectedYear
                        return params
                    }
                }
                q.add(stringRequest)


            }


            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun loadAchievements(achievements: List<Achievement>){
        val orderedList = achievements.mapIndexed { index, achievement ->
            "${index+1}. ${achievement.achievement}"
        }?.joinToString("\n")

        val spannableString = SpannableString(orderedList)

        val lines = orderedList?.lines()
        lines?.forEachIndexed { index, line ->
            val startIndex = orderedList.indexOf(line)
            val endIndex = startIndex+line.length

            val numberPart = line.substringBefore(". ") + "."

            val textPaint = binding.heading.paint
            val numberWidth = textPaint.measureText(numberPart).toInt()

            spannableString.setSpan(
                LeadingMarginSpan.Standard(0, numberWidth),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.achievementList.text = spannableString
    }

    val spannableString = SpannableStringBuilder()
    fun addListItem(number: Int, text: String) {
        val item = "$number. $text"
        spannableString.append(item)
        val bulletSpan = LeadingMarginSpan.Standard(50, 50)
        spannableString.setSpan(bulletSpan, spannableString.length - item.length, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.append("\n")
    }
}
