package dev.niltsiar.anothertodo.domain.usecase

import arrow.core.Either
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import dev.niltsiar.anothertodo.domain.repository.TodoRepository

class AddTodoUseCase(
    private val todoRepository: TodoRepository,
) {
    suspend operator fun invoke(todo: TodoItem): Either<TodoError, Long> {
        return todoRepository.addTodo(todo)
    }
}
