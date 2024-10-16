package com.app.esports.ui.schedule

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
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

            val sharedPreferences: SharedPreferences = view.context.getSharedPreferences("SETTING", Context.MODE_PRIVATE)
            var isNotificationSet = sharedPreferences.getBoolean("NOTIFICATION_SCHEDULE${event.id}", false)

            UpdateBtnNotify(isNotificationSet)

            binding.btnNotify.setOnClickListener{
                isNotificationSet = sharedPreferences.getBoolean("NOTIFICATION_SCHEDULE${event.id}", false)

                val editor = sharedPreferences.edit()
                editor.putBoolean("NOTIFICATION_SCHEDULE${event.id}", !isNotificationSet)
                editor.apply()

                UpdateBtnNotify(!isNotificationSet)

                if (isNotificationSet){
                    Toast.makeText(view.context, "Notification for this event is turned off!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(view.context, "Notification is set successfully!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun UpdateBtnNotify(isNotificationSet:Boolean = false){
        if (isNotificationSet){
            binding.btnNotify.setText("Notification On!")
            binding.btnNotify.setTextColor(Color.WHITE)
            binding.btnNotify.setIconTintResource(R.color.white)
            binding.btnNotify.setBackgroundResource(R.drawable.filled_button)
            binding.btnNotify.setIconResource(R.drawable.outline_alarm_on_24)
        }
        else{
            binding.btnNotify.setText("Notify Me")
            binding.btnNotify.setTextColor(resources.getColor(R.color.primary_red))
            binding.btnNotify.setIconTintResource(R.color.primary_red)
            binding.btnNotify.setBackgroundResource(R.drawable.outlined_button)
            binding.btnNotify.setIconResource(R.drawable.outline_add_alarm_24)
        }
    }
}