package com.lighthouse.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lighthouse.common_ui.adapter.SimpleListAdapter
import com.lighthouse.common_ui.base.BaseFragment
import com.lighthouse.common_ui.util.UiState
import com.lighthouse.common_ui.util.toast
import com.lighthouse.domain.vo.QuestionListVO
import com.lighthouse.domain.vo.QuestionVO
import com.lighthouse.home.databinding.FragmentHomeBinding
import com.lighthouse.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    SimpleListAdapter.ClickListener {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: SimpleListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initAdapter()
        fetchQuestion()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect {
                    render(it)
                }
            }
        }
        checkLocal()
        initSwipe()
    }

    private fun fetchQuestion() {
        viewModel.getQuestionList(20)
        binding.swipeRefresh.isRefreshing = false
    }


    private fun initSwipe() {
        binding.swipeRefresh.setOnRefreshListener {
            fetchQuestion()
        }
    }

    private fun initAdapter() {
        adapter = SimpleListAdapter(this)
        binding.rvHome.adapter = adapter
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            UiState.Loading -> {
                binding.pbHome.visibility = View.VISIBLE
                binding.rvHome.visibility = View.GONE
            }

            is UiState.Success<*> -> {
                binding.rvHome.visibility = View.VISIBLE
                if (uiState.data is QuestionListVO) {
                    val response = uiState.data as QuestionListVO
                    val result = response.questions.toMutableList()
                    result.add(1, QuestionVO())
                    result.add(4, QuestionVO())
                    adapter.questionList = result
                    adapter.notifyDataSetChanged()
                } else {
                    context.toast("${uiState.data}")
                }
                binding.pbHome.visibility = View.GONE
            }

            is UiState.Error<*> -> {
                binding.pbHome.visibility = View.GONE
                displayException(uiState)
            }
        }
    }

    override fun onClick(questionId: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToResultFragment(
                questionId
            )
        )
    }

    private fun checkLocal() {
        viewModel.save("test1", "test", false)
        viewModel.save("test2", true, true)
        viewModel.save("test3", 12313, true)
        viewModel.save("test4", listOf(1, 2, 3, 4), false)
    }
}