package com.app.esports

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.databinding.FragmentApplyTeamBinding
import com.app.esports.databinding.GameButtonCardBinding
import com.app.esports.databinding.TeamApplyCardBinding
import com.app.esports.ui.teams.Team
import com.game.esports.GameAdapter.GameViewHolder
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class TeamGameAdapter(private val games: List<Game>, private val bindingFragment: FragmentApplyTeamBinding, private val activity: MainActivity) : RecyclerView.Adapter<TeamGameAdapter.GameViewHolder>() {

    class TeamsAdapter(private val teams: List<Team>, private val activity: MainActivity) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>(){
        class TeamViewHolder(val binding: TeamApplyCardBinding):RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
            val binding = TeamApplyCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TeamViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return teams.size
        }

        override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
            holder.binding.tvGame.setText(teams[position].name)

            holder.binding.btnApply.setOnClickListener{
                var dialogFragment = SendFormFragment.newInstance(teams[position])
                activity.supportFragmentManager.beginTransaction().let{
                    dialogFragment.show(it, "dialog")
                }
            }
        }
    }
    class GameViewHolder(val binding: GameButtonCardBinding):RecyclerView.ViewHolder(binding.root)

    private var selectedPosition = 0 // Track the selected position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameButtonCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val position = holder.adapterPosition
        holder.binding.btnGame.setText(games[position].name)

        // Update button's background tint based on selected position
        val isSelected = position === selectedPosition
        holder.binding.btnGame.setChecked(isSelected)
        if (isSelected) showTeams(games[position].id, holder)

        holder.binding.btnGame.setOnClickListener { v ->
            val previousPosition: Int = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            showTeams(games[position].id, holder)
        }
    }

    fun showTeams(id: Int, holder: GameViewHolder){
        val q = Volley.newRequestQueue(holder.itemView.context)
        val url = "https://ubaya.xyz/native/160922001/api/get_team.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val sType = object : TypeToken<List<Team>>() { }.type
                    val teams = Gson().fromJson<List<Team>>(obj.getString("data"), sType)

                    bindingFragment.recyclerViewTeams.layoutManager = LinearLayoutManager(holder.itemView.context)
                    bindingFragment.recyclerViewTeams.setHasFixedSize(true)
                    bindingFragment.recyclerViewTeams.adapter = TeamGameAdapter.TeamsAdapter(teams, activity)
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            },
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["game_id"] = id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
}