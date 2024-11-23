package com.app.esports.ui.teams

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.Game
import com.app.esports.R
import com.app.esports.TeamDetails
import com.app.esports.databinding.FragmentTeamsBinding
import com.game.esports.GameAdapter
import com.game.esports.GameAdapter.Companion.GAME
import com.game.esports.GameAdapter.Companion.IMG
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception


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

        val game: Game? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(GameAdapter.GAME, Game::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(GameAdapter.GAME) as? Game
        }

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.xyz/native/160922001/api/get_team.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val sType = object : TypeToken<List<Team>>() { }.type
                    val teams = Gson().fromJson<List<Team>>(obj.getString("data"), sType)

                    val gameName = game?.name
                    val gameImage = game?.image_url
                    if (gameImage != null) {
                        val builder = Picasso.Builder(requireContext())
                        builder.listener { picasso, uri, exception ->
                            exception.printStackTrace()
                        }
                        val img = ImageView(requireContext())
                        builder.build().load(gameImage).into(img, object : Callback {
                            override fun onSuccess() {
                                binding.imgGame.background = img.drawable
                            }

                            override fun onError(e: Exception?) {
                                Log.e("picasso", e?.message.toString())
                            }
                        })
                    }
                    for (t in teams) {
                        val button = MaterialButton(
                            ContextThemeWrapper(
                                this.context,
                                R.style.ButtonTeam
                            )
                        ).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                            ).apply {
                                setMargins(0, 0, 0, 20)
                            }
                            setText(t.name)
                            setBackgroundColor(resources.getColor(R.color.primary_bg))
                            setCornerRadius(42)
                            setStrokeColorResource(R.color.primary_red)
                            setPadding(20, 40, 20, 40)
                        }
                        button.setOnClickListener {
                            val intent = Intent(context, TeamDetails::class.java).apply {
                                putExtra("GAME_IMAGE_URL", gameImage)
                                putExtra("TEAM_ID", t.id)
                                putExtra("TEAM_NAME", t.name)
                            }
                            startActivity(intent)
                        }

                        binding.teams.addView(button)
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            },
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["game_id"] = game?.id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}