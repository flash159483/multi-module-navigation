package com.lighthouse.data.api

import com.lighthouse.data.dto.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface TestAPI {
    @GET("error/test")
    suspend fun getTest(): Response<BaseResponse<String>>
}