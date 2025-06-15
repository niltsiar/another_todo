package dev.niltsiar.anothertodo.domain.usecase

import dev.niltsiar.anothertodo.data.repository.TodoRepository
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.model.toDomain
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {
    operator fun invoke(): Flow<List<TodoItem>> {
        return todoRepository.getAllTodos().map { it.toDomain() }
    }

    fun getActiveTodos(): Flow<List<TodoItem>> {
        return todoRepository.getActiveTodos().map { it.toDomain() }
    }

    fun getCompletedTodos(): Flow<List<TodoItem>> {
        return todoRepository.getCompletedTodos().map { it.toDomain() }
    }
}
