package com.example.todojetpackcompose.common

import com.example.todojetpackcompose.data.api.AuthInterceptor
import com.example.todojetpackcompose.data.api.NoAuthInterceptor
import com.example.todojetpackcompose.data.datastore.AuthenticationService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var instance: Retrofit? = null

    val client: Retrofit?
        get() {
            if(instance == null){
                instance = Retrofit.Builder()
                    .baseUrl("http://192.168.10.106:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance
        }

    fun create(authenticationService: AuthenticationService) {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authenticationService))
            .addInterceptor(NoAuthInterceptor(authenticationService))
            .build()

        instance = Retrofit.Builder()
            .baseUrl("http://192.168.10.106:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}