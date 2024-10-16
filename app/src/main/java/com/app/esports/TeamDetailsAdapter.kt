package com.app.esports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.esports.R
import com.app.esports.StaticData
import com.app.esports.databinding.PlayerCardBinding
import com.app.esports.ui.teams.Player
import com.squareup.picasso.Picasso


class TeamDetailsAdapter(private val players: Array<Player>):RecyclerView.Adapter<TeamDetailsAdapter.PlayerViewHolder>() {
    class PlayerViewHolder(val binding:PlayerCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = PlayerCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.binding.txtPlayerName.text = players[position].name
        holder.binding.txtPlayerRole.text = players[position].role
        val imageUrl = "https://i.pravatar.cc/150"

        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        builder.build().load(imageUrl).into(holder.binding.playerImage)
    }


}