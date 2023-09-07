package com.lighthouse.common_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lighthouse.common_ui.databinding.ListTileBinding
import com.lighthouse.domain.VO.QuestionVO

class SimpleListAdapter(
    private val listener: ClickListener,
) : RecyclerView.Adapter<SimpleViewHolder>() {
    var questionList: List<QuestionVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val binding = ListTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(questionList[position])
        holder.itemView.setOnClickListener {
            listener.onClick(questionList[position].id)
        }
    }

    override fun getItemCount(): Int = questionList.size

    fun interface ClickListener {
        fun onClick(questionId: String)
    }
}