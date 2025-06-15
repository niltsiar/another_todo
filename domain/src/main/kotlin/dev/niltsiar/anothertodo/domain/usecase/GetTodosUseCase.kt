package dev.niltsiar.anothertodo.domain.usecase

import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {
    operator fun invoke(): Flow<List<TodoItem>> {
        return todoRepository.getAllTodos()
    }

    fun getActiveTodos(): Flow<List<TodoItem>> {
        return todoRepository.getActiveTodos()
    }

    fun getCompletedTodos(): Flow<List<TodoItem>> {
        return todoRepository.getCompletedTodos()
    }
}
