package com.lighthouse.data.repository

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.lighthouse.data.local.LocalPreferenceDataSource
import com.lighthouse.data.remote.RemoteConfigDataSource
import com.lighthouse.data.remote.datasource.QuestionDataSource
import com.lighthouse.data.remote.datasource.TestDataSource
import com.lighthouse.domain.repository.QuestionRepository
import com.lighthouse.domain.vo.QuestionContentVO
import com.lighthouse.domain.vo.QuestionListVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val dataSource: QuestionDataSource,
    private val remoteConfigDataSource: RemoteConfigDataSource,
    private val remoteConfig: FirebaseRemoteConfig,
    private val local: LocalPreferenceDataSource,
    private val testDataSource: TestDataSource
) : QuestionRepository {
    override fun getQuestionList(pageSize: Int?): Flow<Result<QuestionListVO>> = flow {
        val response =
            dataSource.getQuestionList(pageSize, remoteConfig.getString("STACKOVERFLOW_API_KEY"))
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.toVO()))
        } else {
            emit(Result.failure(Exception("failed")))
        }
    }

    override fun getQuestionContent(questionId: String?): Flow<Result<QuestionContentVO>> = flow {
        val response = dataSource.getQuestionContent(
            questionId,
            remoteConfig.getString("STACKOVERFLOW_API_KEY")
        )
        Log.d("TESTING", response.body().toString())
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.toVO()))
        } else {
            emit(Result.failure(Exception("failed")))
        }
    }

    override suspend fun fetchRemoteConfig(): Boolean {
        return remoteConfigDataSource.fetchRemoteConfig()
    }

    override fun <T> getValue(key: String, defaultValue: T, isEncrypted: Boolean): T =
        local.getValue(key, defaultValue, isEncrypted)

    override fun <T> save(key: String, value: T, isEncrypted: Boolean) =
        local.save(key, value, isEncrypted)

    override fun getTest(): Flow<String> = testDataSource.getTest()
}