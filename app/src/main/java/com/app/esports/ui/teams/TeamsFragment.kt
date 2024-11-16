package com.app.esports.ui.teams

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.esports.R
import com.app.esports.StaticData
import com.app.esports.TeamDetails
import com.app.esports.databinding.FragmentTeamsBinding
import com.game.esports.GameAdapter
import com.google.android.material.button.MaterialButton

class TeamsFragment : Fragment() {

    private var _binding: FragmentTeamsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(TeamsViewModel::class.java)

        _binding = FragmentTeamsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val team: String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(GameAdapter.GAME, String::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(GameAdapter.GAME) as? String
        }

        val teams = StaticData.teams[team]?.keys?.toList() ?: emptyList()

        for (game in StaticData.games) {
            if (game.name == team) {
                val gameName = game.name
                val gameImageId = game.image_url
                binding.imgGame.setBackgroundResource(game.image_url)

                for (t in teams) {
                    val button = MaterialButton(ContextThemeWrapper(this.context, R.style.ButtonTeam)).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                        ).apply {
                            setMargins(0, 0, 0, 20)
                        }
                        setText(t)
                        setBackgroundColor(resources.getColor(R.color.primary_bg))
                        setCornerRadius(42)
                        setStrokeColorResource(R.color.primary_red)
                        setPadding(20, 40, 20, 40)
                    }
                    button.setOnClickListener {
                        val teamName = t
                        val intent = Intent(context, TeamDetails::class.java).apply {
                            putExtra("GAME_NAME", gameName)
                            putExtra("GAME_IMAGE_ID", gameImageId)
                            putExtra("TEAM_NAME", teamName)
                        }
                        startActivity(intent)
                    }

                    binding.teams.addView(button)
                }
                break
            }
        }



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}