package dev.niltsiar.anothertodo.presentation.viewmodel

import app.cash.turbine.test
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import dev.niltsiar.anothertodo.domain.usecase.AddTodoUseCase
import dev.niltsiar.anothertodo.domain.usecase.DeleteTodoUseCase
import dev.niltsiar.anothertodo.domain.usecase.GetTodoByIdUseCase
import dev.niltsiar.anothertodo.domain.usecase.GetTodosUseCase
import dev.niltsiar.anothertodo.domain.usecase.UpdateTodoUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import arrow.core.right
import arrow.core.left

@OptIn(ExperimentalCoroutinesApi::class)
class TodoViewModelTest {
    
    private val testDispatcher = StandardTestDispatcher()
    
    private lateinit var getTodosUseCase: GetTodosUseCase
    private lateinit var getTodoByIdUseCase: GetTodoByIdUseCase
    private lateinit var addTodoUseCase: AddTodoUseCase
    private lateinit var updateTodoUseCase: UpdateTodoUseCase
    private lateinit var deleteTodoUseCase: DeleteTodoUseCase
    
    private lateinit var viewModel: TodoViewModel
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        getTodosUseCase = mockk()
        getTodoByIdUseCase = mockk()
        addTodoUseCase = mockk()
        updateTodoUseCase = mockk()
        deleteTodoUseCase = mockk()
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `loadTodos should update state with todos`() = runTest {
        // Given
        val todos = listOf(
            TodoItem(id = 1, title = "Test Todo 1"),
            TodoItem(id = 2, title = "Test Todo 2")
        )
        coEvery { getTodosUseCase() } returns flowOf(todos)
        
        viewModel = TodoViewModel(
            getTodosUseCase,
            getTodoByIdUseCase,
            addTodoUseCase,
            updateTodoUseCase,
            deleteTodoUseCase
        )
        
        // When & Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading shouldBe true
            
            val loadedState = awaitItem()
            loadedState.isLoading shouldBe false
            loadedState.todos shouldBe todos
        }
    }
    
    @Test
    fun `getTodoById should update state with selected todo`() = runTest {
        // Given
        val todo = TodoItem(id = 1, title = "Test Todo")
        coEvery { getTodosUseCase() } returns flowOf(emptyList())
        coEvery { getTodoByIdUseCase(1) } returns todo.right()
        
        viewModel = TodoViewModel(
            getTodosUseCase,
            getTodoByIdUseCase,
            addTodoUseCase,
            updateTodoUseCase,
            deleteTodoUseCase
        )
        
        // When
        viewModel.getTodoById(1)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            state.selectedTodo shouldBe todo
        }
    }
    
    @Test
    fun `getTodoById should update state with error when todo not found`() = runTest {
        // Given
        coEvery { getTodosUseCase() } returns flowOf(emptyList())
        coEvery { getTodoByIdUseCase(1) } returns TodoError.TodoNotFound.left()
        
        viewModel = TodoViewModel(
            getTodosUseCase,
            getTodoByIdUseCase,
            addTodoUseCase,
            updateTodoUseCase,
            deleteTodoUseCase
        )
        
        // When
        viewModel.getTodoById(1)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            state.error shouldBe "Todo not found"
        }
    }
}
