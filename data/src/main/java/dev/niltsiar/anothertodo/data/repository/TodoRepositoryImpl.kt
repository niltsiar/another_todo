package dev.niltsiar.anothertodo.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import dev.niltsiar.anothertodo.data.local.TodoDao
import dev.niltsiar.anothertodo.data.mapper.toDomain
import dev.niltsiar.anothertodo.data.mapper.toData
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
) : TodoRepository {

    override fun getAllTodos(): Flow<List<TodoItem>> = todoDao.getAllTodos().map { it.toDomain() }

    override fun getActiveTodos(): Flow<List<TodoItem>> = todoDao.getActiveTodos().map { it.toDomain() }

    override fun getCompletedTodos(): Flow<List<TodoItem>> = todoDao.getCompletedTodos().map { it.toDomain() }

    override suspend fun getTodoById(id: Long): Either<TodoError, TodoItem> {
        return try {
            val todo = todoDao.getTodoById(id)
            if (todo != null) {
                todo.toDomain().right()
            } else {
                TodoError.TodoNotFound.left()
            }
        } catch (e: Exception) {
            TodoError.UnknownError(e.message ?: "Unknown error").left()
        }
    }

    override suspend fun addTodo(todo: TodoItem): Either<TodoError, Long> {
        return try {
            val id = todoDao.insertTodo(todo.toData())
            id.right()
        } catch (e: Exception) {
            TodoError.DatabaseError.left()
        }
    }

    override suspend fun updateTodo(todo: TodoItem): Either<TodoError, Unit> {
        return try {
            todoDao.updateTodo(todo.toData())
            Unit.right()
        } catch (e: Exception) {
            TodoError.DatabaseError.left()
        }
    }

    override suspend fun deleteTodo(todo: TodoItem): Either<TodoError, Unit> {
        return try {
            todoDao.deleteTodo(todo.toData())
            Unit.right()
        } catch (e: Exception) {
            TodoError.DatabaseError.left()
        }
    }

    override suspend fun deleteTodoById(id: Long): Either<TodoError, Unit> {
        return try {
            todoDao.deleteTodoById(id)
            Unit.right()
        } catch (e: Exception) {
            TodoError.DatabaseError.left()
        }
    }

    override suspend fun deleteAllTodos(): Either<TodoError, Unit> {
        return try {
            todoDao.deleteAllTodos()
            Unit.right()
        } catch (e: Exception) {
            TodoError.DatabaseError.left()
        }
    }
}
