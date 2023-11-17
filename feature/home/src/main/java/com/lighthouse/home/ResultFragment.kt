package com.lighthouse.home

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.lighthouse.common_ui.util.UiState
import com.lighthouse.domain.vo.QuestionContentVO
import com.lighthouse.home.databinding.FragmentResultBinding
import com.lighthouse.home.viewmodel.HomeViewModel
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
        checkLocal()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect {
                    render(it)
                }
            }
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

    private fun checkLocal() {
        val t1 = viewModel.getValue("test1", "", false)
        val t2 = viewModel.getValue("test2", false, true)
        val t3 = viewModel.getValue("test3", 123, true)
        val t4 = viewModel.getValue("test4", "", false)

        Log.d("TESTING LOCAL", "$t1 $t2 $t3 $t4")
    }
}

