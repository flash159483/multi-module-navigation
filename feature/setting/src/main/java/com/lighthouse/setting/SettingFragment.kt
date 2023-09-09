package com.lighthouse.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lighthouse.navigation.NavigationFlow
import com.lighthouse.navigation.ToFlowNavigatable
import com.lighthouse.setting.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val args: SettingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(args.data != "") {
            binding.tvResult.text = args.data
        }

        binding.btnToHome.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.HomeFlow)
        }

        val result = args.msgs.split(" ")
        if(result.size > 1) {
            Log.d("TESTING", result.toString())
            binding.btnBack.visibility = View.VISIBLE
            binding.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}