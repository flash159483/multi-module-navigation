package com.lighthouse.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lighthouse.home.databinding.FragmentResultBinding
import com.lighthouse.navigation.NavigationFlow
import com.lighthouse.navigation.ToFlowNavigatable

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.data.let {
            binding.tvResult.text = it
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}