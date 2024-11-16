package com.game.esports

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.app.esports.StaticData
import com.app.esports.AchievementActivity
import com.app.esports.R
import com.app.esports.databinding.GameCardBinding
import com.app.esports.ui.teams.TeamsFragment

class GameAdapter(private val navController: NavController):RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    class GameViewHolder(val binding:GameCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }
    override fun onBindViewHolder(holder: GameViewHolder, position: Int){
        holder.binding.txtGame.setText(StaticData.games[position].name)
        holder.binding.txtDesc.setText(StaticData.games[position].description)
        holder.binding.imgGame.setImageResource(StaticData.games[position].image_url)

        holder.binding.btnTeams.setOnClickListener{
            val bundle = Bundle()
            val entryList = StaticData.teams.entries.toList()

            bundle.putSerializable(GAME, entryList[position].key)

            val targetFragment = TeamsFragment()
            targetFragment.arguments = bundle

            navController.navigate(R.id.nav_teams, bundle)
        }

        holder.binding.btnAchievement.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, AchievementActivity::class.java)

            intent.putExtra(GAME, StaticData.games[position].name)
            intent.putExtra(IMG, StaticData.games[position].image_url)

            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return StaticData.games.size
    }

    companion object{
        val GAME = "game_selected"
        val IMG = "image_from_game"
    }
}