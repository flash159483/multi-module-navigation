package com.lighthouse.domain.constant

class TestException(
    val code: Int,
    override var message: String?,
    var errorType: ErrorType = ErrorType.NONE,
) : Exception() {
    fun addErrorMsg(): TestException {
        val errorType = findApiErrorType()

        if (this.message.isNullOrEmpty()) {
            this.message = errorType.uiMessage
            this.errorType = errorType.errorType
        }

        return this
    }

    private fun findApiErrorType(): ApiErrorType {
        return ApiErrorType.values().find { it.code == code }
            ?: ApiErrorType.UNKNOWN
    }
}