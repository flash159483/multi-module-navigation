package com.lighthouse.multi_module_navigation.di

import android.content.SharedPreferences
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.lighthouse.data.api.StackOverFlowAPI
import com.lighthouse.data.api.TestAPI
import com.lighthouse.data.local.LocalPreferenceDataSource
import com.lighthouse.data.local.LocalPreferenceDataSourceImpl
import com.lighthouse.data.remote.RemoteConfigDataSource
import com.lighthouse.data.remote.datasource.QuestionDataSource
import com.lighthouse.data.remote.datasource.TestDataSource
import com.lighthouse.data.remote.datasourceimpl.QuestionDataSourceImpl
import com.lighthouse.data.remote.datasourceimpl.TestDataSourceImpl
import com.lighthouse.data.repository.QuestionRepositoryImpl
import com.lighthouse.domain.repository.QuestionRepository
import com.lighthouse.multi_module_navigation.R
import com.lighthouse.multi_module_navigation.di.annotation.DefaultPreference
import com.lighthouse.multi_module_navigation.di.annotation.EncryptedPreference
import com.lighthouse.multi_module_navigation.di.annotation.Main
import com.lighthouse.multi_module_navigation.di.annotation.Test
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
    fun provideQuestionDataSource(@Main api: StackOverFlowAPI): QuestionDataSource {
        return QuestionDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideTestDataSource(@Test api: TestAPI): TestDataSource {
        return TestDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(
        dataSource: QuestionDataSource,
        remoteConfigDataSource: RemoteConfigDataSource,
        remoteConfig: FirebaseRemoteConfig,
        testDataSource: TestDataSource,
        local: LocalPreferenceDataSource
    ): QuestionRepository {
        return QuestionRepositoryImpl(
            dataSource,
            remoteConfigDataSource,
            remoteConfig,
            local,
            testDataSource
        )
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

    @Provides
    @Singleton
    fun provideLocalDataSource(
        @DefaultPreference sharedPreferences: SharedPreferences,
        @EncryptedPreference encryptedSharedPreferences: SharedPreferences
    ): LocalPreferenceDataSource {
        return LocalPreferenceDataSourceImpl(sharedPreferences, encryptedSharedPreferences)
    }
}