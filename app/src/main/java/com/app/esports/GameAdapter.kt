package com.game.esports

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.StaticData
import com.app.esports.AchievementActivity
import com.app.esports.Game
import com.app.esports.R
import com.app.esports.databinding.GameCardBinding
import com.app.esports.ui.schedule.Schedule
import com.app.esports.ui.teams.TeamsFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class GameAdapter(private val games: List<Game>, private val navController: NavController):RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(val binding:GameCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }
    override fun onBindViewHolder(holder: GameViewHolder, position: Int){
        val position = holder.adapterPosition
        holder.binding.txtGame.setText(games[position].name)
        holder.binding.txtDesc.setText(games[position].description)

        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        val img = ImageView(holder.itemView.context)
        builder.build().load(games[position].image_url).into(img, object : Callback {
            override fun onSuccess() {
                Log.d("picassoinfo", games[position].image_url)
                holder.binding.imgGame.setImageDrawable(img.drawable)
            }

            override fun onError(e: Exception?) {
                Log.e("picassoerror", e?.message.toString())
            }
        })

        holder.binding.btnTeams.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable(GAME, games[position])

            val targetFragment = TeamsFragment()
            targetFragment.arguments = bundle

            navController.navigate(R.id.nav_teams, bundle)
        }

        holder.binding.btnAchievement.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, AchievementActivity::class.java)

            intent.putExtra(GAME, games[position].name)
            intent.putExtra(IMG, games[position].image_url)

            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return games.size
    }

    companion object{
        val GAME = "game_selected"
        val IMG = "image_from_game"
    }
}