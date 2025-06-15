package dev.niltsiar.anothertodo.domain.usecase

import arrow.core.Either
import dev.niltsiar.anothertodo.data.repository.TodoError
import dev.niltsiar.anothertodo.data.repository.TodoRepository
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.model.toData
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {
    suspend operator fun invoke(todo: TodoItem): Either<TodoError, Unit> {
        return todoRepository.updateTodo(todo.toData())
    }
}
