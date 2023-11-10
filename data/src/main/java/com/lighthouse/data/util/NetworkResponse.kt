package com.lighthouse.data.util

import com.google.gson.Gson
import com.lighthouse.data.dto.BaseResponse
import com.lighthouse.data.dto.ErrorDTO
import com.lighthouse.domain.constant.ErrorType
import com.lighthouse.domain.constant.TestException
import retrofit2.Response

abstract class NetworkResponse {
    protected fun <T, R : BaseResponse<T>> changeResult(response: Response<R>): T {
        if (response.isSuccessful) {
            return response.body()!!.data
        } else {
            throw errorHandler(response)
        }
    }

    private fun <R> errorHandler(response: Response<R>): TestException {
        val errorBody = response.errorBody()?.string()

        return try {
            val errorResponse: BaseResponse<*> =
                Gson().fromJson(errorBody, BaseResponse::class.java)

            val errorMsg = errorResponse.data?.toString() ?: "{}"

            val body = errorMsg.getErrorMsg()
            val code = errorResponse.code
            val message = body.message ?: errorResponse.message

            val errorType = ErrorType.fromString(body.type ?: "NONE")
            TestException(code, message, errorType).addErrorMsg()
        } catch (e: Exception) {
            TestException(response.code(), null).addErrorMsg()
        }
    }

    private fun String.getErrorMsg(): ErrorDTO {
        if (this.isEmpty()) {
            // Handle the case of an empty error string
            return ErrorDTO(null, null)
        }

        val keyValuePairs = this
            .substring(1, this.length - 1) // Remove curly braces
            .split(", ")
            .map { it.split("=") }
            .filter { it.size == 2 } // Filter out pairs that don't have exactly one "="
            .associate { it[0] to it[1] }

        return ErrorDTO(keyValuePairs["uiMessage"] ?: "", keyValuePairs["type"] ?: "")
    }
}