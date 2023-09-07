package com.lighthouse.multi_module_navigation.di

import com.lighthouse.domain.repository.QuestionRepository
import com.lighthouse.domain.usecase.QuestionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideQuestionUseCase(repository: QuestionRepository): QuestionUseCase {
        return QuestionUseCase(repository)
    }
}