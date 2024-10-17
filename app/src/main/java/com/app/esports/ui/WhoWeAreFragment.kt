package com.app.esports.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.esports.R
import com.app.esports.StaticData
import com.app.esports.databinding.FragmentScheduleBinding
import com.app.esports.databinding.FragmentWhoWeAreBinding
import com.app.esports.ui.schedule.ScheduleAdapter
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WhoWeAreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WhoWeAreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private var _binding: FragmentWhoWeAreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWhoWeAreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences = view.context.getSharedPreferences("SETTING", Context.MODE_PRIVATE)
        var isLiked = sharedPreferences.getBoolean("IS_LIKED", false)
        var numOfLikes = sharedPreferences.getInt("LIKE_NUM", 0)

        if (isLiked) {
            binding.btnLike.setIconResource(R.drawable.like_icon_active)
        }
        else {
            binding.btnLike.setIconResource(R.drawable.like_icon)
        }
        binding.btnLike.text = numOfLikes.toString()

        binding.btnLike.setOnClickListener{
            isLiked = sharedPreferences.getBoolean("IS_LIKED", false)
            numOfLikes = sharedPreferences.getInt("LIKE_NUM", 0)

            if (isLiked) {
                showUnlikeDialog(numOfLikes, sharedPreferences)
            } else {
                numOfLikes++
                binding.btnLike.setIconResource(R.drawable.like_icon_active)
                binding.btnLike.text = numOfLikes.toString()

                val editor = sharedPreferences.edit()
                editor.putBoolean("IS_LIKED", true)
                editor.putInt("LIKE_NUM", numOfLikes)
                editor.apply()
            }
        }

        binding.imgPlay.setOnClickListener{
            binding.parentWebView.visibility = View.VISIBLE
            loadMovie()
        }
        binding.textView2.setOnClickListener {
            showOpenLinkDialog()
        }
    }
    private fun showUnlikeDialog(numOfLikes: Int, sharedPreferences: SharedPreferences) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Unlike Confirmation")
        builder.setMessage("Are you sure you want to unlike?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            var updatedLikes = numOfLikes - 1
            binding.btnLike.setIconResource(R.drawable.like_icon)
            binding.btnLike.text = updatedLikes.toString()

            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_LIKED", false)
            editor.putInt("LIKE_NUM", updatedLikes)
            editor.apply()

            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun loadMovie() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.mediaPlaybackRequiresUserGesture = false

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressWebView.visibility = View.INVISIBLE
            }
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return true
            }
        }
        val videoId = "bqjJBxDRPl4"
        val youtubeUrl = "https://www.youtube.com/embed/$videoId?autoplay=1&mute=0&controls=1"

        binding.webView.loadUrl(youtubeUrl)
    }
    private fun openLink() {
        val url = "https://masseffect.fandom.com/wiki/Eclipse_Vanguard"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    private fun showOpenLinkDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Open Link")
        builder.setMessage("Do you want to open Eclipse Vanguard's page?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            openLink()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WhoWeAreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                WhoWeAreFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}