package com.app.esports.ui.proposal

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.esports.R
import com.app.esports.databinding.ProposalCardBinding

class ProposalAdapter(private val proposals: List<proposal>) : RecyclerView.Adapter<ProposalAdapter.ProposalViewHolder>() {

    private val expandedItems = mutableSetOf<Int>()
    inner class ProposalViewHolder(val binding: ProposalCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProposalViewHolder {
        val binding = ProposalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProposalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProposalViewHolder, position: Int) {
        val proposal = proposals[position]
        holder.binding.proposalTitle.text = "${proposal.name}"
        holder.binding.proposalStatus.text = proposal.status
        holder.binding.applyDate.text = "Apply Date: ${proposal.created_at}"
        holder.binding.gameName.text = "Game: ${proposal.game_name}"
        holder.binding.username.text = "Username: ${proposal.username}"
        holder.binding.description.text = "Description: ${proposal.description}"

        val context = holder.itemView.context
        val indicatorDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_indication_prop)!!

        when (proposal.status) {
            "Granted" -> indicatorDrawable.setTint(Color.parseColor("#43E7AD"))
            "Declined" -> indicatorDrawable.setTint(Color.parseColor("#FF0000"))
            "Waiting" -> indicatorDrawable.setTint(Color.parseColor("#FFA600"))
            else -> indicatorDrawable.setTint(Color.GRAY)
        }
        holder.binding.statusIndicator.setImageDrawable(indicatorDrawable)

        if (expandedItems.contains(proposal.id)) {
            holder.binding.detailsContainer.visibility = View.VISIBLE
        } else {
            holder.binding.detailsContainer.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            if (expandedItems.contains(proposal.id)) {
                expandedItems.remove(proposal.id)
                holder.binding.detailsContainer.visibility = View.GONE
                holder.binding.btnShow.setText("Show Details")
            } else {
                expandedItems.add(proposal.id)
                holder.binding.detailsContainer.visibility = View.VISIBLE
                holder.binding.btnShow.setText("Hide Details")
            }
        }

        holder.binding.btnShow.setOnClickListener{
            holder.itemView.performClick()
        }
    }


    override fun getItemCount(): Int {
        return proposals.size
    }
}
