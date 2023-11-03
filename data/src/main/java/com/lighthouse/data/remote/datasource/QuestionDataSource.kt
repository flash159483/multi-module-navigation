package com.lighthouse.data.remote.datasource

import com.lighthouse.data.dto.QuestionBodyDTO
import com.lighthouse.data.dto.QuestionListDTO
import retrofit2.Response

interface QuestionDataSource {
    suspend fun getQuestionList(pageSize: Int?, api: String): Response<QuestionListDTO>
    suspend fun getQuestionContent(questionId: String?, api: String): Response<QuestionBodyDTO>
}