package com.example.todojetpackcompose.domain.use_case

import com.example.todojetpackcompose.domain.repository.ListRepository
import javax.inject.Inject

class RemoveListUseCase @Inject constructor(
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(listId: Int) {
        listRepository.deleteList(listId)
    }
}