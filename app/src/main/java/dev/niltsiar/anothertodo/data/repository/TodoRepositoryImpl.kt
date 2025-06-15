package dev.niltsiar.anothertodo.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import dev.niltsiar.anothertodo.data.local.TodoDao
import dev.niltsiar.anothertodo.data.model.TodoItem
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
) : TodoRepository {

    override fun getAllTodos(): Flow<List<TodoItem>> = todoDao.getAllTodos()

    override fun getActiveTodos(): Flow<List<TodoItem>> = todoDao.getActiveTodos()

    override fun getCompletedTodos(): Flow<List<TodoItem>> = todoDao.getCompletedTodos()

    override suspend fun getTodoById(id: Long): Either<TodoError, TodoItem> {
        return try {
            val todo = todoDao.getTodoById(id)
            if (todo != null) {
                todo.right()
            } else {
                TodoError.TodoNotFound.left()
            }
        } catch (e: Exception) {
            TodoError.UnknownError(e.message ?: "Unknown error").left()
        }
    }

    override suspend fun addTodo(todo: TodoItem): Either<TodoError, Long> {
        return try {
            val id = todoDao.insertTodo(todo)
            id.right()
        } catch (e: Exception) {
            TodoError.DatabaseError.left()
        }
    }

    override suspend fun updateTodo(todo: TodoItem): Either<TodoError, Unit> {
        return try {
            todoDao.updateTodo(todo)
            Unit.right()
        } catch (e: Exception) {
            TodoError.DatabaseError.left()
        }
    }

    override suspend fun deleteTodo(todo: TodoItem): Either<TodoError, Unit> {
        return try {
            todoDao.deleteTodo(todo)
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
