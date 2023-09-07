package com.lighthouse.domain.repository

import com.lighthouse.domain.VO.QuestionContentVO
import com.lighthouse.domain.VO.QuestionListVO
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getQuestionList(pageSize: Int?): Flow<Result<QuestionListVO>>
    fun getQuestionContent(questionId: String?): Flow<Result<QuestionContentVO>>
}