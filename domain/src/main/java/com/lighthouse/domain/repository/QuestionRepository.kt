package com.lighthouse.domain.repository

import com.lighthouse.domain.vo.QuestionContentVO
import com.lighthouse.domain.vo.QuestionListVO
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getQuestionList(pageSize: Int?): Flow<Result<QuestionListVO>>
    fun getQuestionContent(questionId: String?): Flow<Result<QuestionContentVO>>

    suspend fun fetchRemoteConfig(): Boolean

    fun getTest(): Flow<String>

    fun <T> save(key: String, value: T, isEncrypted: Boolean)
    fun <T> getValue(key: String, defaultValue: T, isEncrypted: Boolean): T
}