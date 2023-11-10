package com.lighthouse.home

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lighthouse.common_ui.util.UiState
import com.lighthouse.domain.vo.QuestionContentVO
import com.lighthouse.home.databinding.FragmentResultBinding
import com.lighthouse.home.viewmodel.HomeViewModel
import com.lighthouse.navigation.DeepLinkDestination
import com.lighthouse.navigation.deepLinkNavigateTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
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
        viewModel.getQuestionContent(args.questionId)
        initSetting()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect {
                    render(it)
                }
            }
        }
    }

    private fun initSetting() {
        binding.toSetting.setOnClickListener {
            findNavController().deepLinkNavigateTo(DeepLinkDestination.Setting, true)
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            UiState.Loading -> {
                binding.tvBody.visibility = View.GONE
            }

            is UiState.Success<*> -> {
                binding.tvBody.visibility = View.VISIBLE
                val response = uiState.data as QuestionContentVO
                binding.tvBody.text = Html.fromHtml(response.body, Html.FROM_HTML_MODE_LEGACY)
            }

            is UiState.Error<*> -> {
            }
        }
    }
}