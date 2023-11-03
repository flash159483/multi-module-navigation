package com.lighthouse.data.remote.datasourceimpl

import com.lighthouse.data.api.StackOverFlowAPI
import com.lighthouse.data.dto.QuestionBodyDTO
import com.lighthouse.data.dto.QuestionListDTO
import com.lighthouse.data.remote.datasource.QuestionDataSource
import retrofit2.Response
import javax.inject.Inject

class QuestionDataSourceImpl @Inject constructor(
    private val service: StackOverFlowAPI,
) : QuestionDataSource {
    override suspend fun getQuestionList(pageSize: Int?, api: String): Response<QuestionListDTO> =
        service.lastActiveQuestions(pageSize, api)

    override suspend fun getQuestionContent(
        questionId: String?,
        api: String
    ): Response<QuestionBodyDTO> =
        service.questionDetails(questionId, api)
}