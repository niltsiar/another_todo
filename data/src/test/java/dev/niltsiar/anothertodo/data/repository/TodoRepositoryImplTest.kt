package dev.niltsiar.anothertodo.data.repository

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import dev.niltsiar.anothertodo.data.local.TodoDao
import dev.niltsiar.anothertodo.data.model.TodoItem as DataTodoItem
import dev.niltsiar.anothertodo.domain.model.TodoItem as DomainTodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TodoRepositoryImplTest {
    
    private lateinit var todoDao: TodoDao
    private lateinit var repository: TodoRepositoryImpl
    
    @Before
    fun setup() {
        todoDao = mockk()
        repository = TodoRepositoryImpl(todoDao)
    }
    
    @Test
    fun `getAllTodos should return mapped todos from dao`() = runTest {
        // Given
        val dataTodos = listOf(
            DataTodoItem(id = 1, title = "Test Todo 1"),
            DataTodoItem(id = 2, title = "Test Todo 2")
        )
        every { todoDao.getAllTodos() } returns flowOf(dataTodos)
        
        // When & Then
        repository.getAllTodos().test {
            val result = awaitItem()
            result.size shouldBe 2
            result[0].id shouldBe 1
            result[0].title shouldBe "Test Todo 1"
            result[1].id shouldBe 2
            result[1].title shouldBe "Test Todo 2"
            awaitComplete()
        }
    }
    
    @Test
    fun `getTodoById should return Right with todo when found`() = runTest {
        // Given
        val dataTodo = DataTodoItem(id = 1, title = "Test Todo")
        coEvery { todoDao.getTodoById(1) } returns dataTodo
        
        // When
        val result = repository.getTodoById(1)
        
        // Then
        result shouldBe dataTodo.toDomain().right()
    }
    
    @Test
    fun `getTodoById should return Left with TodoNotFound when not found`() = runTest {
        // Given
        coEvery { todoDao.getTodoById(1) } returns null
        
        // When
        val result = repository.getTodoById(1)
        
        // Then
        result shouldBe TodoError.TodoNotFound.left()
    }
    
    @Test
    fun `addTodo should return Right with id when successful`() = runTest {
        // Given
        val domainTodo = DomainTodoItem(title = "Test Todo")
        coEvery { todoDao.insertTodo(any()) } returns 1
        
        // When
        val result = repository.addTodo(domainTodo)
        
        // Then
        result shouldBe 1L.right()
        coVerify { todoDao.insertTodo(any()) }
    }
    
    @Test
    fun `addTodo should return Left with DatabaseError when exception occurs`() = runTest {
        // Given
        val domainTodo = DomainTodoItem(title = "Test Todo")
        coEvery { todoDao.insertTodo(any()) } throws RuntimeException("Database error")
        
        // When
        val result = repository.addTodo(domainTodo)
        
        // Then
        result shouldBe TodoError.DatabaseError.left()
    }
}
