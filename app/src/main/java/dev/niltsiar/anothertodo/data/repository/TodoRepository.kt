package dev.niltsiar.anothertodo.data.repository

import arrow.core.Either
import dev.niltsiar.anothertodo.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAllTodos(): Flow<List<TodoItem>>
    fun getActiveTodos(): Flow<List<TodoItem>>
    fun getCompletedTodos(): Flow<List<TodoItem>>
    suspend fun getTodoById(id: Long): Either<TodoError, TodoItem>
    suspend fun addTodo(todo: TodoItem): Either<TodoError, Long>
    suspend fun updateTodo(todo: TodoItem): Either<TodoError, Unit>
    suspend fun deleteTodo(todo: TodoItem): Either<TodoError, Unit>
    suspend fun deleteTodoById(id: Long): Either<TodoError, Unit>
    suspend fun deleteAllTodos(): Either<TodoError, Unit>
}

sealed class TodoError {
    data object DatabaseError : TodoError()
    data object TodoNotFound : TodoError()
    data class UnknownError(val message: String) : TodoError()
}
