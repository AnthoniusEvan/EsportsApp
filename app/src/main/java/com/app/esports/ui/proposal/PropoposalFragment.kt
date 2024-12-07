package com.app.esports.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.MainActivity
import com.app.esports.R
import com.app.esports.databinding.FragmentProposalBinding
import com.app.esports.ui.proposal.ProposalAdapter
import com.app.esports.ui.proposal.proposal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class ProposalFragment : Fragment() {

    private var _binding: FragmentProposalBinding? = null
    private val binding get() = _binding!!
    private var userId: Int? = null
    private var selectedButton: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProposalBinding.inflate(inflater, container, false)
        userId = (activity as? MainActivity)?.active_user?.id
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.nav_apply_team)
        }
        binding.allButton.setOnClickListener { loadProposals("All") }
        binding.waitingButton.setOnClickListener { loadProposals("Waiting") }
        binding.grantedButton.setOnClickListener { loadProposals("Granted") }
        binding.declinedButton.setOnClickListener { loadProposals("Declined") }

        loadProposals("All")
    }

    private fun loadProposals(status: String) {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://ubaya.xyz/native/160922001/api/get_proposal.php"

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            { response ->
                Log.d("apiresult", response)
                val obj = JSONObject(response)
                if (obj.getString("result") == "OK") {
                    val sType = object : TypeToken<List<proposal>>() {}.type
                    val proposals = Gson().fromJson<List<proposal>>(obj.getString("data"), sType)

                    binding.propCard.layoutManager = LinearLayoutManager(context)
                    binding.propCard.setHasFixedSize(true)
                    binding.propCard.adapter = ProposalAdapter(proposals)
                } else {
                    Log.e("apiresult", "No proposals found!")
                }
            },
            { error ->
                Log.e("apiresult", error.message.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf("status" to status, "user_id" to userId.toString())
            }
        }

        queue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_USER_ID = "user_id"

        @JvmStatic
        fun newInstance(userId: Int) =
            ProposalFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_USER_ID, userId)
                }
            }
    }
}
