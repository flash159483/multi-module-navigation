package com.lighthouse.domain.usecase

import com.lighthouse.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionUseCase @Inject constructor(
    private val repository: QuestionRepository,
) {
    fun getQuestionList(pageSize: Int?) = repository.getQuestionList(pageSize)

    fun getQuestionContent(questionId: String?) = repository.getQuestionContent(questionId)
}