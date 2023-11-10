package com.lighthouse.domain.constant

enum class ErrorType {
    TOAST,
    DIALOG,
    DIRECT_AND_DIALOG,
    NONE;

    companion object {
        fun fromString(value: String): ErrorType {
            values().find { it.name == value }?.let {
                return it
            } ?: return NONE
        }
    }
}