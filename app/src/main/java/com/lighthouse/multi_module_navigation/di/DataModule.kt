package com.lighthouse.multi_module_navigation.di

import com.lighthouse.data.api.StackOverFlowAPI
import com.lighthouse.data.remote.datasource.QuestionDataSource
import com.lighthouse.data.remote.datasourceimpl.QuestionDataSourceImpl
import com.lighthouse.data.repository.QuestionRepositoryImpl
import com.lighthouse.domain.repository.QuestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideQuestionDataSource(api: StackOverFlowAPI): QuestionDataSource {
        return QuestionDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(dataSource: QuestionDataSource): QuestionRepository {
        return QuestionRepositoryImpl(dataSource)
    }
}