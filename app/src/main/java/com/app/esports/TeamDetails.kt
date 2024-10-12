package com.app.esports

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.esports.databinding.ActivityTeamDetailsBinding

class TeamDetails : AppCompatActivity() {
    private lateinit var binding: ActivityTeamDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gameImageId = intent.getIntExtra("GAME_IMAGE_ID", R.drawable.csgo)
        val gameName = intent.getStringExtra("GAME_NAME") ?: "Default Game"
        val teamName = intent.getStringExtra("TEAM_NAME") ?: "Default Team"

        val players = StaticData.teams[gameName]?.get(teamName)
        binding.frame1.setBackgroundResource(gameImageId)
        binding.teamName.text = teamName

        if (players != null) {
            val adapter = TeamDetailsAdapter(players)
            binding.playerList.layoutManager = LinearLayoutManager(this)
            binding.playerList.setHasFixedSize(true)
            binding.playerList.adapter = adapter
        }
    }
}
