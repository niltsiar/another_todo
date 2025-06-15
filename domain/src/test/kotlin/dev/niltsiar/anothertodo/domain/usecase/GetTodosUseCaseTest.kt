package dev.niltsiar.anothertodo.domain.usecase

import app.cash.turbine.test
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTodosUseCaseTest {
    
    private lateinit var repository: TodoRepository
    private lateinit var getTodosUseCase: GetTodosUseCase
    
    @Before
    fun setup() {
        repository = mockk()
        getTodosUseCase = GetTodosUseCase(repository)
    }
    
    @Test
    fun `invoke should return todos from repository`() = runTest {
        // Given
        val todos = listOf(
            TodoItem(id = 1, title = "Test Todo 1"),
            TodoItem(id = 2, title = "Test Todo 2")
        )
        every { repository.getAllTodos() } returns flowOf(todos)
        
        // When & Then
        getTodosUseCase().test {
            val result = awaitItem()
            result shouldBe todos
            awaitComplete()
        }
    }
    
    @Test
    fun `getActiveTodos should return active todos from repository`() = runTest {
        // Given
        val activeTodos = listOf(
            TodoItem(id = 1, title = "Test Todo 1", isCompleted = false),
            TodoItem(id = 2, title = "Test Todo 2", isCompleted = false)
        )
        every { repository.getActiveTodos() } returns flowOf(activeTodos)
        
        // When & Then
        getTodosUseCase.getActiveTodos().test {
            val result = awaitItem()
            result shouldBe activeTodos
            awaitComplete()
        }
    }
    
    @Test
    fun `getCompletedTodos should return completed todos from repository`() = runTest {
        // Given
        val completedTodos = listOf(
            TodoItem(id = 1, title = "Test Todo 1", isCompleted = true),
            TodoItem(id = 2, title = "Test Todo 2", isCompleted = true)
        )
        every { repository.getCompletedTodos() } returns flowOf(completedTodos)
        
        // When & Then
        getTodosUseCase.getCompletedTodos().test {
            val result = awaitItem()
            result shouldBe completedTodos
            awaitComplete()
        }
    }
}
