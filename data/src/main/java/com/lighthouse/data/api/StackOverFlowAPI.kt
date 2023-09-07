package com.lighthouse.data.api

import com.lighthouse.data.dto.QuestionBodyDTO
import com.lighthouse.data.dto.QuestionListDTO
import com.lighthouse.domain.constant.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackOverFlowAPI {
    @GET("/questions?key=" + Constants.STACKOVERFLOW_API_KEY + "&order=desc&sort=activity&site=stackoverflow")
    suspend fun lastActiveQuestions(@Query("pagesize") pageSize: Int?): Response<QuestionListDTO>

    @GET("/questions/{questionId}?key=" + Constants.STACKOVERFLOW_API_KEY + "&site=stackoverflow&filter=withbody")
    suspend fun questionDetails(@Path("questionId") questionId: String?): Response<QuestionBodyDTO>
}