package com.lighthouse.data.dto

import com.google.gson.annotations.SerializedName

data class QuestionBodyDTO(
    @SerializedName("items")
    val questions: List<QuestionContentDTO>,
) {
    fun toVO() = questions[0].toVO()
}