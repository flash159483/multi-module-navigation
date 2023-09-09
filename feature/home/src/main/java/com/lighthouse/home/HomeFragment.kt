package com.lighthouse.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lighthouse.common_ui.adapter.SimpleListAdapter
import com.lighthouse.common_ui.util.UiState
import com.lighthouse.domain.vo.QuestionListVO
import com.lighthouse.home.databinding.FragmentHomeBinding
import com.lighthouse.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), SimpleListAdapter.ClickListener {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: SimpleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        fetchQuestion()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect {
                    render(it)
                }
            }
        }
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
                val response = uiState.data as QuestionListVO
                adapter.questionList = response.questions
                adapter.notifyDataSetChanged()
                binding.pbHome.visibility = View.GONE
            }

            is UiState.Error -> {
                binding.pbHome.visibility = View.GONE
                Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
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
}