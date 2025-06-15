package dev.niltsiar.anothertodo.domain.usecase

import arrow.core.Either
import dev.niltsiar.anothertodo.data.repository.TodoError
import dev.niltsiar.anothertodo.data.repository.TodoRepository
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.model.toData
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {
    suspend operator fun invoke(todo: TodoItem): Either<TodoError, Unit> {
        return todoRepository.deleteTodo(todo.toData())
    }

    suspend fun deleteById(id: Long): Either<TodoError, Unit> {
        return todoRepository.deleteTodoById(id)
    }

    suspend fun deleteAll(): Either<TodoError, Unit> {
        return todoRepository.deleteAllTodos()
    }
}
