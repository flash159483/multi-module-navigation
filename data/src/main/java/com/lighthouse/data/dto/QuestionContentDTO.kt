package com.lighthouse.data.dto

import com.google.gson.annotations.SerializedName
import com.lighthouse.domain.VO.QuestionContentVO

data class QuestionContentDTO(
    @SerializedName("title") val
    title: String?,
    @SerializedName("question_id")
    val id: String?,
    @SerializedName("body")
    val body: String?
) {
    fun toVO() = QuestionContentVO(
        title = title ?: "",
        id = id ?: "-1",
        body = body ?: ""
    )
}