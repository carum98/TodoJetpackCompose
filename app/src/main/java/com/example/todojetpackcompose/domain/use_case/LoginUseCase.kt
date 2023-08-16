package com.example.todojetpackcompose.domain.use_case

import com.example.todojetpackcompose.common.Resource
import com.example.todojetpackcompose.domain.model.Auth
import com.example.todojetpackcompose.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(username: String, password: String): Flow<Resource<Auth>> = flow {
        try {
            emit(Resource.Loading())
            val auth = repository.login(username, password)
            emit(Resource.Success(auth))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}