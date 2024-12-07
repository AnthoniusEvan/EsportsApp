package com.app.esports

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.databinding.FragmentApplyTeamBinding
import com.app.esports.databinding.FragmentSendFormBinding
import com.app.esports.ui.auth.AuthActivity
import com.app.esports.ui.teams.Team
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


class SendFormFragment : DialogFragment() {
    private var team: Team? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            team = it.getParcelable<Team>(ARG_TEAM)
        }
    }
    private lateinit var binding: FragmentSendFormBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = FragmentSendFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        binding.tvGameTeam.setText("Apply to ${team?.name}")
        binding.btnSend.setOnClickListener{
            val q = Volley.newRequestQueue(requireContext())
            val url = "https://ubaya.xyz/native/160922001/api/send_proposal.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("apiresult", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "OK"){
                        Snackbar.make(requireContext(), (activity as MainActivity).binding.root, "Application sent successfully!", Snackbar.LENGTH_SHORT).show()
                        dismiss()
                    }
                    else{
                        Snackbar.make(requireContext(), (activity as MainActivity).binding.root, "Failed to send application, please try again later", Snackbar.LENGTH_LONG).show()
                        dismiss()
                    }
                },
                {
                    Log.e("apiresult", it.message.toString())
                },
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["user_id"] = (activity as MainActivity).active_user.id.toString()
                    params["game_id"] = team?.game_id.toString()
                    params["team_id"] = team?.id.toString()
                    params["description"] = binding.etDesc.text.toString()
                    return params
                }
            }
            q.add(stringRequest)
        }
    }
    companion object {
        private val ARG_TEAM = "team"
        @JvmStatic
        fun newInstance(team: Team) =
            SendFormFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_TEAM, team)
                }
            }
    }
}