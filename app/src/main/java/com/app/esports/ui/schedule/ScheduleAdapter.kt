package com.app.esports.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.esports.StaticData
import com.app.esports.databinding.ScheduleCardBinding

class ScheduleAdapter(): RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
    class ScheduleViewHolder(val binding: ScheduleCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ScheduleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.binding.image.setImageResource(StaticData.schedules[position].imageId)
        holder.binding.txtEventName.text = StaticData.schedules[position].title
        holder.binding.txtDate.text = StaticData.schedules[position].date
        holder.binding.txtTeam.text = "Played by "+StaticData.schedules[position].team
    }
    override fun getItemCount(): Int {
        return StaticData.schedules.size
    }
}