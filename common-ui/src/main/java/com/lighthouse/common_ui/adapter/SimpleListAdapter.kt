package com.lighthouse.common_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lighthouse.common_ui.databinding.ListTileBinding
import com.lighthouse.common_ui.databinding.NativeAdBinding
import com.lighthouse.domain.vo.QuestionVO

class SimpleListAdapter(
    private val listener: ClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var questionList: List<QuestionVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_QUESTION -> {
                val binding = ListTileBinding.inflate(inflater, parent, false)
                SimpleViewHolder(binding)
            }

            TYPE_AD -> {
                val binding = NativeAdBinding.inflate(inflater, parent, false)
                AdViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SimpleViewHolder -> {
                holder.bind(questionList[position])
                holder.itemView.setOnClickListener {
                    listener.onClick(questionList[position].id)
                }
            }

            is AdViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int = questionList.size

    override fun getItemViewType(position: Int): Int {
        return if (questionList[position].id == "ad") {
            TYPE_AD
        } else {
            TYPE_QUESTION
        }
    }

    fun interface ClickListener {
        fun onClick(questionId: String)
    }


    companion object {
        private const val TYPE_QUESTION = 0
        private const val TYPE_AD = 1
    }
}