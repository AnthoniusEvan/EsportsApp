package com.app.esports.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.Achievement
import com.app.esports.MainActivity
import com.app.esports.R
import com.app.esports.StaticData
import com.app.esports.databinding.FragmentScheduleBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class ScheduleFragment : Fragment(), ScheduleAdapter.OnItemClickListener {
    private lateinit var schedules:List<Schedule>
    private var _binding: FragmentScheduleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scheduleViewModel =
            ViewModelProvider(this).get(ScheduleViewModel::class.java)

        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val q = Volley.newRequestQueue(requireContext())
        val url = "https://ubaya.xyz/native/160922001/api/get_schedules.php"
        val stringRequest = StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val sType = object : TypeToken<List<Schedule>>() { }.type
                    schedules = Gson().fromJson<List<Schedule>>(obj.getString("data"), sType)
                    binding.recSchedule.layoutManager = LinearLayoutManager(this.context)
                    binding.recSchedule.setHasFixedSize(true)
                    binding.recSchedule.adapter = ScheduleAdapter(schedules, this)
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            },
        )
        q.add(stringRequest)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(EVENT, schedules[position])

        val targetFragment = ScheduleDetailFragment()
        targetFragment.arguments = bundle

        findNavController().navigate(R.id.nav_schedule_detail, bundle)
        (activity as MainActivity).toggleMainAndAdditionalFragments(false)
    }

    companion object{
        val EVENT="event_schedule"
    }
}