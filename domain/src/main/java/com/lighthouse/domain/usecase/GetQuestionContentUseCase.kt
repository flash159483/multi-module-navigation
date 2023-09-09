package com.lighthouse.domain.usecase

import com.lighthouse.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionContentUseCase @Inject constructor(
    private val repository: QuestionRepository,
) {
    fun invoke(questionId: String?) = repository.getQuestionContent(questionId)
}