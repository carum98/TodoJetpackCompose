package com.example.todojetpackcompose.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class Code204Interceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        return if (response.code() == 204) {
            response.newBuilder().code(200).build()
        } else {
            response
        }
    }
}