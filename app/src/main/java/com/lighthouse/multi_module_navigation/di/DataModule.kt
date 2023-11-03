package com.lighthouse.multi_module_navigation.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.lighthouse.data.api.StackOverFlowAPI
import com.lighthouse.data.remote.RemoteConfigDataSource
import com.lighthouse.data.remote.datasource.QuestionDataSource
import com.lighthouse.data.remote.datasourceimpl.QuestionDataSourceImpl
import com.lighthouse.data.repository.QuestionRepositoryImpl
import com.lighthouse.domain.repository.QuestionRepository
import com.lighthouse.multi_module_navigation.R
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
    fun provideQuestionRepository(
        dataSource: QuestionDataSource,
        remoteConfigDataSource: RemoteConfigDataSource,
        remoteConfig: FirebaseRemoteConfig
    ): QuestionRepository {
        return QuestionRepositoryImpl(dataSource, remoteConfigDataSource, remoteConfig)
    }

    @Provides
    @Singleton
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = FirebaseRemoteConfig.getInstance().apply {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()

            setDefaultsAsync(R.xml.remote_default_config)
            setConfigSettingsAsync(configSettings)
        }
        return remoteConfig
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(remoteConfig: FirebaseRemoteConfig): RemoteConfigDataSource {
        return RemoteConfigDataSource(remoteConfig)
    }
}