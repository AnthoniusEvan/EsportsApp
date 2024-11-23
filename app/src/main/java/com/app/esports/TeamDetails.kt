package com.app.esports

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.databinding.ActivityTeamDetailsBinding
import com.app.esports.ui.teams.Player
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class TeamDetails : AppCompatActivity() {
    private lateinit var binding: ActivityTeamDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gameImage = intent.getStringExtra("GAME_IMAGE_URL")
        val teamId = intent.getIntExtra("TEAM_ID", 1)
        val teamName = intent.getStringExtra("TEAM_NAME")

        val builder = Picasso.Builder(this)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        val imgV = ImageView(this)
        builder.build().load(gameImage).into(imgV, object : Callback {
            override fun onSuccess() {
                binding.frame1.background = imgV.drawable
            }

            override fun onError(e: Exception?) {
                Log.e("picassoerror", e?.message.toString())
            }
        })

        //val players = StaticData.teams[gameName]?.get(teamName)

        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160922001/api/get_players.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val sType = object : TypeToken<List<Player>>() { }.type
                    val players = Gson().fromJson<List<Player>>(obj.getString("data"), sType)

                    if (players != null) {
                        val adapter = TeamDetailsAdapter(players)
                        binding.playerList.layoutManager = LinearLayoutManager(this)
                        binding.playerList.setHasFixedSize(true)
                        binding.playerList.adapter = adapter
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            },
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["team_id"] = teamId.toString()
                return params
            }
        }
        q.add(stringRequest)

        binding.teamName.text = teamName
    }
}
