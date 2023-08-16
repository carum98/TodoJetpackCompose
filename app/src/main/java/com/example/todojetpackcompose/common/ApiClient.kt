package com.example.todojetpackcompose.common

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var instance: Retrofit? = null

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
}