package com.example.todojetpackcompose.di

import com.example.todojetpackcompose.data.api.AuthInterceptor
import com.example.todojetpackcompose.data.api.AuthService
import com.example.todojetpackcompose.data.api.ListService
import com.example.todojetpackcompose.data.api.NoAuthInterceptor
import com.example.todojetpackcompose.data.repository.AuthRepositoryImpl
import com.example.todojetpackcompose.data.repository.ListRepositoryImpl
import com.example.todojetpackcompose.domain.repository.AuthRepository
import com.example.todojetpackcompose.domain.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        authInterceptor: AuthInterceptor,
        noAuthInterceptor: NoAuthInterceptor,
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(noAuthInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://192.168.10.106:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideListService(retrofit: Retrofit): ListService {
        return retrofit.create(ListService::class.java)
    }

    @Provides
    @Singleton
    fun provideListRepository(listService: ListService): ListRepository {
        return ListRepositoryImpl(listService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }
}