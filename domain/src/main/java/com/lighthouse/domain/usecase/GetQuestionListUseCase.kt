package com.lighthouse.domain.usecase

import com.lighthouse.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionListUseCase @Inject constructor(
    private val repository: QuestionRepository,
) {
    fun invoke(pageSize: Int?) = repository.getQuestionList(pageSize)
}