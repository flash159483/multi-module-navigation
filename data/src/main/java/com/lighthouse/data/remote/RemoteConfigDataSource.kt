package com.lighthouse.data.remote

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

@Singleton
class RemoteConfigDataSource @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) {
    suspend fun fetchRemoteConfig(): Boolean {
        return suspendCoroutine { continuation ->
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resumeWith(Result.success(true))
                } else {
                    continuation.resumeWith(
                        Result.failure(
                            task.exception ?: Exception("Unknown error")
                        )
                    )
                }
            }
        }
    }
}