package com.app.esports.ui.schedule

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.esports.R
import com.app.esports.databinding.FragmentScheduleDetailBinding
import com.app.esports.databinding.FragmentWhoWeAreBinding

class ScheduleDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentScheduleDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event: Schedule? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(ScheduleFragment.EVENT, Schedule::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(ScheduleFragment.EVENT) as? Schedule
        }

        if (event != null) {
            binding.txtTitle.text = event.title
            binding.txtTeam.text = event.team
            binding.img.setImageResource(event.imageId)
            binding.btnNotify.setOnClickListener{
                Toast.makeText(this.context, "Notification is set successfully!", Toast.LENGTH_LONG).show()
            }
        }
    }

}