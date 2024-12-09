package com.app.esports.ui.schedule

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.esports.StaticData
import com.app.esports.databinding.ScheduleCardBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.text.SimpleDateFormat

class ScheduleAdapter(private val schedules:List<Schedule>, private val listener: OnItemClickListener): RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ScheduleViewHolder(val binding: ScheduleCardBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.scheduleCard.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ScheduleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val position = holder.adapterPosition
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        builder.build().load(schedules[position].image_url).into(holder.binding.image)

        holder.binding.txtEventName.text = schedules[position].title

        val parser =  SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("dd MMM")
        val formattedDate = formatter.format(parser.parse(schedules[position].date))

        holder.binding.txtDate.text = formattedDate
        holder.binding.txtTeam.text = "Played by ${schedules[position].team_name}"

    }
    override fun getItemCount(): Int {
        return schedules.size
    }
}