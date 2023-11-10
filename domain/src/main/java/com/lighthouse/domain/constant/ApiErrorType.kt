package com.lighthouse.domain.constant

enum class ApiErrorType(
    val code: Int?,
    val uiMessage: String,
    val errorType: ErrorType
) {
    SERVER_ERROR(
        500,
        "Server error. Please try again later.",
        ErrorType.DIALOG
    ),

    UNKNOWN(
        null,
        "Unknown error. Please try again later.",
        ErrorType.TOAST
    );
}