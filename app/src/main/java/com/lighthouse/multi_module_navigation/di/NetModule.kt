package com.lighthouse.multi_module_navigation.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.lighthouse.data.api.StackOverFlowAPI
import com.lighthouse.data.api.TestAPI
import com.lighthouse.multi_module_navigation.RemoteConfigInterceptor
import com.lighthouse.multi_module_navigation.di.annotation.Main
import com.lighthouse.multi_module_navigation.di.annotation.Test
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
    @Main
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
    @Main
    fun provideStackOverFlowRetrofit(
        @Main okHttpClient: OkHttpClient,
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
    @Test
    fun provideTestRetrofit(@Test okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://demo3624522.mockable.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Test
    fun provideTestClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    @Main
    fun provideStackOverFlowAPI(@Main retrofit: Retrofit): StackOverFlowAPI {
        return retrofit.create(StackOverFlowAPI::class.java)
    }

    @Provides
    @Singleton
    @Test
    fun provideTestAPI(@Test retrofit: Retrofit): TestAPI {
        return retrofit.create(TestAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteInterceptor(remoteConfig: FirebaseRemoteConfig): RemoteConfigInterceptor {
        return RemoteConfigInterceptor(remoteConfig)
    }
}