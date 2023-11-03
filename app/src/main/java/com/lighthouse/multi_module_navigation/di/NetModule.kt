package com.lighthouse.multi_module_navigation.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.lighthouse.data.api.StackOverFlowAPI
import com.lighthouse.multi_module_navigation.RemoteConfigInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {
    @Provides
    @Singleton
    fun provideStackOverFlowClient(
        remoteConfigInterceptor: RemoteConfigInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(remoteConfigInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideStackOverFlowRetrofit(
        okHttpClient: OkHttpClient,
        remoteConfig: FirebaseRemoteConfig
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(remoteConfig.getString("BASE_URL"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStackOverFlowAPI(retrofit: Retrofit): StackOverFlowAPI {
        return retrofit.create(StackOverFlowAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteInterceptor(remoteConfig: FirebaseRemoteConfig): RemoteConfigInterceptor {
        return RemoteConfigInterceptor(remoteConfig)
    }
}