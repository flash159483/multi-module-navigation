package com.lighthouse.data.dto

import com.google.gson.annotations.SerializedName
import com.lighthouse.domain.VO.QuestionVO

data class QuestionDTO(
    @SerializedName("title")
    val title: String?,
    @SerializedName("question_id")
    val id: String?,
) {
    fun toVO() = QuestionVO(
        title = title ?: "",
        id = id ?: "-1"
    )
}