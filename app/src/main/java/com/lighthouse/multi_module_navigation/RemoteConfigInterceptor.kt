package com.lighthouse.multi_module_navigation

import android.net.Uri
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RemoteConfigInterceptor @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Fetch the updated base URL from Remote Config
        val fullUrl = runBlocking {
            remoteConfig.getString("BASE_URL")
        }

        val baseUrl = Uri.parse(fullUrl)

        // Replace the base URL part of the request URL with the updated base URL
        val newUrl = originalUrl.newBuilder()
            .scheme("https") // or "http" based on your requirement
            .host(baseUrl.host ?: "")
            .build()

        // Create a new request with the updated URL and the same method, headers, and body as the original request
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        // Proceed with the new request
        return chain.proceed(newRequest)
    }
}

