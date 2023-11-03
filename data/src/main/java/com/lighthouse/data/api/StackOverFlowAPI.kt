package com.lighthouse.data.api

import com.lighthouse.data.dto.QuestionBodyDTO
import com.lighthouse.data.dto.QuestionListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackOverFlowAPI {
    @GET("/questions?order=desc&sort=activity&site=stackoverflow")
    suspend fun lastActiveQuestions(
        @Query(
            "pagesize",
        ) pageSize: Int?,
        @Query("key") api: String
    ): Response<QuestionListDTO>

    @GET("/questions/{questionId}?site=stackoverflow&filter=withbody")
    suspend fun questionDetails(
        @Path("questionId") questionId: String?,
        @Query("key") api: String
    ): Response<QuestionBodyDTO>
}