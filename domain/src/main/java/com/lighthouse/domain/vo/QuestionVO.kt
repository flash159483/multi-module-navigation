package com.lighthouse.domain.vo

data class QuestionVO(
    val title: String,
    val id: String,
) {
    constructor() : this("", "ad")
}
