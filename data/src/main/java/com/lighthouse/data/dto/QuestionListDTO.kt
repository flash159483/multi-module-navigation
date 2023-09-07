package com.lighthouse.data.dto

import com.google.gson.annotations.SerializedName
import com.lighthouse.domain.VO.QuestionListVO

data class QuestionListDTO(
    @SerializedName("items")
    val questions: List<QuestionDTO>?,
) {
    fun toVO() = QuestionListVO(
        questions = questions?.map { it.toVO() } ?: listOf()
    )
}