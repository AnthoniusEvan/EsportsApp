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
import com.google.android.material.button.MaterialButton
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
    private var selectedPosition = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.nav_apply_team)
        }
        binding.allButton.setOnClickListener {
            val filters = arrayOf(it, binding.waitingButton, binding.grantedButton, binding.declinedButton)
            for (filter in filters){
                if (filter != it){
                    (filter as MaterialButton).isChecked = false
                }
                else (filter as MaterialButton).isChecked = true
            }
            loadProposals("All")
        }
        binding.waitingButton.setOnClickListener {
            val filters = arrayOf(it, binding.allButton, binding.grantedButton, binding.declinedButton)
            for (filter in filters){
                if (filter != it){
                    (filter as MaterialButton).isChecked = false
                }
                else (filter as MaterialButton).isChecked = true
            }
            loadProposals("Waiting")
        }
        binding.grantedButton.setOnClickListener {
            val filters = arrayOf(it, binding.waitingButton, binding.allButton, binding.declinedButton)
            for (filter in filters){
                if (filter != it){
                    (filter as MaterialButton).isChecked = false
                }
                else (filter as MaterialButton).isChecked = true
            }
            loadProposals("Granted")
        }
        binding.declinedButton.setOnClickListener {
            val filters = arrayOf(it, binding.waitingButton, binding.grantedButton, binding.allButton)
            for (filter in filters){
                if (filter != it){
                    (filter as MaterialButton).isChecked = false
                }
                else (filter as MaterialButton).isChecked = true
            }
            loadProposals("Declined")
        }

        if ((activity as MainActivity).active_user.role == "Admin") {
            binding.fabAdd.visibility = View.GONE
            val filters = arrayOf(binding.allButton, binding.waitingButton, binding.grantedButton, binding.declinedButton)
            for (filter in filters){
                if (filter != binding.waitingButton){
                    (filter as MaterialButton).isChecked = false
                }
                else (filter as MaterialButton).isChecked = true
            }
            loadProposals("Waiting")
        }
        else {
            binding.allButton.isChecked=true;
            loadProposals("All")
        }
    }

    public fun loadProposals(status: String) {
        var showAll = false;
        if ((activity as MainActivity).active_user.role == "Admin"){
            showAll = true;
        }

        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://ubaya.xyz/native/160922001/api/get_proposal.php"

        if (showAll){
            url = "https://ubaya.xyz/native/160922001/api/get_all_proposal.php"
        }
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            { response ->
                Log.d("apiresult", response)
                val obj = JSONObject(response)
                if (obj.getString("result") == "OK") {
                    val sType = object : TypeToken<List<proposal>>() {}.type
                    val proposals = Gson().fromJson<List<proposal>>(obj.getString("data"), sType)

                    binding.propCard.layoutManager = LinearLayoutManager(context)
                    binding.propCard.setHasFixedSize(true)
                    binding.propCard.adapter = ProposalAdapter(proposals, this)
                } else {
                    Log.e("apiresult", "No proposals found!")
                }
            },
            { error ->
                Log.e("apiresult", error.message.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                if (showAll) return hashMapOf("status" to status)
                else return hashMapOf("status" to status, "user_id" to userId.toString())
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
