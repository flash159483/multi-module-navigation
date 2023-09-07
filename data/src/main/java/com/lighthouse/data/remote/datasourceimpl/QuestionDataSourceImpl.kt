package com.lighthouse.data.remote.datasourceimpl

import com.lighthouse.data.api.StackOverFlowAPI
import com.lighthouse.data.dto.QuestionBodyDTO
import com.lighthouse.data.dto.QuestionListDTO
import com.lighthouse.data.remote.datasource.QuestionDataSource
import retrofit2.Response
import javax.inject.Inject

class QuestionDataSourceImpl @Inject constructor(
    private val api: StackOverFlowAPI,
) : QuestionDataSource {
    override suspend fun getQuestionList(pageSize: Int?): Response<QuestionListDTO> =
        api.lastActiveQuestions(pageSize)

    override suspend fun getQuestionContent(questionId: String?): Response<QuestionBodyDTO> =
        api.questionDetails(questionId)
}