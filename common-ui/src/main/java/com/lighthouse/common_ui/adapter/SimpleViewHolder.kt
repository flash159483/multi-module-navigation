package com.lighthouse.common_ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.lighthouse.common_ui.databinding.ListTileBinding
import com.lighthouse.domain.VO.QuestionVO

class SimpleViewHolder(
    private val binding: ListTileBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(question: QuestionVO) {
        binding.tvTitle.text = question.title
    }
}