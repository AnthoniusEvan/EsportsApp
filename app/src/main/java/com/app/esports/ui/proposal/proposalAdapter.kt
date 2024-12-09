package com.app.esports.ui.proposal

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.MainActivity
import com.app.esports.R
import com.app.esports.User
import com.app.esports.databinding.ProposalCardBinding
import com.app.esports.ui.ProposalFragment
import org.json.JSONObject

class ProposalAdapter(private val proposals: List<proposal>, private val fragment: ProposalFragment) : RecyclerView.Adapter<ProposalAdapter.ProposalViewHolder>() {

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

        if ((fragment.activity as MainActivity).active_user.role == "Admin"){
            if (proposal.status == "Waiting") {
                holder.binding.detailsContainer.visibility = View.VISIBLE
                holder.binding.btnShow.visibility = View.GONE;
                holder.binding.btnAccept.visibility = View.VISIBLE
                holder.binding.btnDecline.visibility = View.VISIBLE
            }
        }
        holder.binding.btnAccept.setOnClickListener{
            val q = Volley.newRequestQueue(fragment.activity)
            val url = "https://ubaya.xyz/native/160922001/api/set_proposal_status.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("apiresult", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "OK"){
                        fragment.loadProposals("Waiting");
                        Toast.makeText(fragment.activity, "Application Granted!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(fragment.activity, obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                },
                {
                    Log.e("apiresult", it.message.toString())
                },
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val params = mutableMapOf<String, String>()
                    params["id"] = proposal.id.toString()
                    params["status"] = "Granted"
                    return params
                }
            }
            q.add(stringRequest)
        }

        holder.binding.btnDecline.setOnClickListener{
            val q = Volley.newRequestQueue(fragment.activity)
            val url = "https://ubaya.xyz/native/160922001/api/set_proposal_status.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("apiresult", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "OK"){
                        fragment.loadProposals("Waiting");
                        Toast.makeText(fragment.activity, "Application Declined!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(fragment.activity, obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                },
                {
                    Log.e("apiresult", it.message.toString())
                },
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val params = mutableMapOf<String, String>()
                    params["id"] = proposal.id.toString()
                    params["status"] = "Declined"
                    return params
                }
            }
            q.add(stringRequest)
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
