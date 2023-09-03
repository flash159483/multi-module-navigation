package com.lighthouse.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.lighthouse.home.databinding.FragmentHomeBinding
import com.lighthouse.navigation.NavigationFlow
import com.lighthouse.navigation.ToFlowNavigatable

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSend.setOnClickListener {
            val data = binding.etSend.text.toString()
            val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(data)
            findNavController().navigate(action)
        }

        binding.btnToSetting.setOnClickListener {
            val data = binding.etSend.text.toString()
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.SettingFlow(data))
        }
    }
}