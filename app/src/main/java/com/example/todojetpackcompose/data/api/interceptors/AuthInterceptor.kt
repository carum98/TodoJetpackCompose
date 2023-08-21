package com.example.todojetpackcompose.data.api.interceptors

import com.example.todojetpackcompose.data.datastore.AuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authenticationService: AuthenticationService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking(Dispatchers.IO) {
        val request = chain
            .request()
            .newBuilder()

        try {
            val token = authenticationService.getToken()
            request.addHeader("Authorization", "Bearer $token")
        } catch (e: Exception) {
            authenticationService.onLogout()
        }

        return@runBlocking chain.proceed(request.build())
    }
}

class NoAuthInterceptor @Inject constructor(
    private val authenticationService: AuthenticationService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking(Dispatchers.IO) {
        val originalRequest: Request = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code() == 401) {
            authenticationService.onLogout()
        }

        return@runBlocking response
    }
}