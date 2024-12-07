package com.app.esports

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.esports.databinding.FragmentApplyTeamBinding
import com.app.esports.databinding.FragmentSendFormBinding
import com.app.esports.ui.teams.Team


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