package com.example.todojetpackcompose.domain.use_case

import com.example.todojetpackcompose.domain.repository.ListRepository

class GetLists(
    private val listRepository: ListRepository
) {
    suspend operator fun invoke() = listRepository.getLists()
}