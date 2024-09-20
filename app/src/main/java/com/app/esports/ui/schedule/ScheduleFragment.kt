package com.app.esports.ui.schedule

import android.os.Bundle
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
import com.app.esports.R
import com.app.esports.StaticData
import com.app.esports.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment(), ScheduleAdapter.OnItemClickListener {

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

//        val textView: TextView = binding.textSlideshow
//        scheduleViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recSchedule.layoutManager = LinearLayoutManager(this.context)
        binding.recSchedule.setHasFixedSize(true)
        binding.recSchedule.adapter = ScheduleAdapter(this)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(EVENT, StaticData.schedules[position])

        val targetFragment = ScheduleDetailFragment()
        targetFragment.arguments = bundle

        findNavController().navigate(R.id.nav_schedule_detail, bundle)
    }

    companion object{
        val EVENT="event_schedule"
    }
}