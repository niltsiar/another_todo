package dev.niltsiar.anothertodo.domain.usecase

import arrow.core.Either
import dev.niltsiar.anothertodo.data.repository.TodoError
import dev.niltsiar.anothertodo.data.repository.TodoRepository
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.model.toDomain
import javax.inject.Inject

class GetTodoByIdUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {
    suspend operator fun invoke(id: Long): Either<TodoError, TodoItem> {
        return todoRepository.getTodoById(id).map { it.toDomain() }
    }
}
