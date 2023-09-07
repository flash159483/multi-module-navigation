package com.lighthouse.data.repository

import android.util.Log
import com.lighthouse.data.remote.datasource.QuestionDataSource
import com.lighthouse.domain.VO.QuestionContentVO
import com.lighthouse.domain.VO.QuestionListVO
import com.lighthouse.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val dataSource: QuestionDataSource,
) : QuestionRepository {
    override fun getQuestionList(pageSize: Int?): Flow<Result<QuestionListVO>> = flow {
        val response = dataSource.getQuestionList(pageSize)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.toVO()))
        } else {
            emit(Result.failure(Exception("failed")))
        }
    }

    override fun getQuestionContent(questionId: String?): Flow<Result<QuestionContentVO>> = flow {
        val response = dataSource.getQuestionContent(questionId)
        Log.d("TESTING", response.body().toString())
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.toVO()))
        } else {
            emit(Result.failure(Exception("failed")))
        }
    }
}