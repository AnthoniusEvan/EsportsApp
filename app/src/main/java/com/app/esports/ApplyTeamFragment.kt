package com.app.esports

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.databinding.FragmentApplyTeamBinding
import com.app.esports.databinding.FragmentScheduleDetailBinding
import com.app.esports.ui.schedule.Schedule
import com.app.esports.ui.schedule.ScheduleAdapter
import com.game.esports.GameAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


class ApplyTeamFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    private var _binding: FragmentApplyTeamBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApplyTeamBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.xyz/native/160922001/api/get_games.php"
        val stringRequest = StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val sType = object : TypeToken<List<Game>>() { }.type
                    val games = Gson().fromJson<List<Game>>(obj.getString("data"), sType)

                    var lm = LinearLayoutManager(this.context);
                    lm.orientation = LinearLayoutManager.HORIZONTAL
                    binding.recyclerViewGame.layoutManager = lm
                    binding.recyclerViewGame.setHasFixedSize(true)
                    binding.recyclerViewGame.adapter = TeamGameAdapter(games, binding, activity as MainActivity)
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            },
        )
        q.add(stringRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ApplyTeamFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}