package com.app.esports.ui

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
import com.app.esports.Game
import com.app.esports.R
import com.app.esports.databinding.FragmentWhatweplayBinding
import com.app.esports.databinding.FragmentWhoWeAreBinding
import com.game.esports.GameAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WhatWePlayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WhatWePlayFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private var _binding: FragmentWhatweplayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWhatweplayBinding.inflate(inflater, container, false)
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
                    binding.recView.layoutManager = LinearLayoutManager(this.context)
                    binding.recView.setHasFixedSize(true)
                    binding.recView.adapter = GameAdapter(games, this.findNavController())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            },
        )
        q.add(stringRequest)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment whatweplay.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WhatWePlayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}