package com.app.esports.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.esports.R
import com.app.esports.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the button in the included layout using ViewBinding
        binding.frame1.setOnClickListener{
            findNavController().navigate(R.id.nav_whatweplay)
        }
        binding.frame2.setOnClickListener {
            findNavController().navigate(R.id.nav_whoweare)
        }
        binding.frame3.setOnClickListener {
            findNavController().navigate(R.id.nav_schedule)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}