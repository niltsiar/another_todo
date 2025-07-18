package dev.niltsiar.anothertodo.presentation.viewmodel

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import dev.niltsiar.anothertodo.domain.model.Priority
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import dev.niltsiar.anothertodo.domain.usecase.AddTodoUseCase
import dev.niltsiar.anothertodo.domain.usecase.DeleteTodoUseCase
import dev.niltsiar.anothertodo.domain.usecase.GetTodoByIdUseCase
import dev.niltsiar.anothertodo.domain.usecase.GetTodosUseCase
import dev.niltsiar.anothertodo.domain.usecase.UpdateTodoUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class TodoViewModelTest : StringSpec({
    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "loadTodos should update state with todos" {
        checkAll(Arb.int(min = 0, max = 5)) { count ->
            runTest {
                // Given
                val getTodosUseCase = mockk<GetTodosUseCase>()
                val getTodoByIdUseCase = mockk<GetTodoByIdUseCase>()
                val addTodoUseCase = mockk<AddTodoUseCase>()
                val updateTodoUseCase = mockk<UpdateTodoUseCase>()
                val deleteTodoUseCase = mockk<DeleteTodoUseCase>()

                val todos = List(count) { index ->
                    TodoItem(
                        id = (index + 1).toLong(),
                        title = "Test Todo ${index + 1}",
                        description = "Description for todo ${index + 1}",
                        isCompleted = index % 2 == 0,
                        priority = when (index % 3) {
                            0 -> Priority.LOW
                            1 -> Priority.MEDIUM
                            else -> Priority.HIGH
                        }
                    )
                }
                coEvery { getTodosUseCase() } returns flowOf(todos)

                val viewModel = TodoViewModel(
                    getTodosUseCase,
                    getTodoByIdUseCase,
                    addTodoUseCase,
                    updateTodoUseCase,
                    deleteTodoUseCase
                )

                // Advance the dispatcher to allow the init block to execute
                testDispatcher.scheduler.advanceUntilIdle()

                // When & Then
                viewModel.uiState.test {
                    val state = awaitItem()
                    state.isLoading shouldBe false
                    state.todos shouldBe todos.toImmutableList()
                }
            }
        }
    }

    "getTodoById should update state with selected todo" {
        checkAll(Arb.long(min = 1), Arb.string(minSize = 1, maxSize = 100)) { todoId, title ->
            runTest {
                // Given
                val getTodosUseCase = mockk<GetTodosUseCase>()
                val getTodoByIdUseCase = mockk<GetTodoByIdUseCase>()
                val addTodoUseCase = mockk<AddTodoUseCase>()
                val updateTodoUseCase = mockk<UpdateTodoUseCase>()
                val deleteTodoUseCase = mockk<DeleteTodoUseCase>()

                val todo = TodoItem(id = todoId, title = title)
                coEvery { getTodosUseCase() } returns flowOf(emptyList())
                coEvery { getTodoByIdUseCase(todoId) } returns todo.right()

                val viewModel = TodoViewModel(
                    getTodosUseCase,
                    getTodoByIdUseCase,
                    addTodoUseCase,
                    updateTodoUseCase,
                    deleteTodoUseCase
                )

                // When
                viewModel.getTodoById(todoId)
                testDispatcher.scheduler.advanceUntilIdle()

                // Then
                viewModel.uiState.test {
                    val state = awaitItem()
                    state.selectedTodo shouldBe todo
                }
            }
        }
    }

    "getTodoById should update state with error when todo not found" {
        checkAll(Arb.long(min = 1)) { todoId ->
            runTest {
                // Given
                val getTodosUseCase = mockk<GetTodosUseCase>()
                val getTodoByIdUseCase = mockk<GetTodoByIdUseCase>()
                val addTodoUseCase = mockk<AddTodoUseCase>()
                val updateTodoUseCase = mockk<UpdateTodoUseCase>()
                val deleteTodoUseCase = mockk<DeleteTodoUseCase>()

                coEvery { getTodosUseCase() } returns flowOf(emptyList())
                coEvery { getTodoByIdUseCase(todoId) } returns TodoError.TodoNotFound.left()

                val viewModel = TodoViewModel(
                    getTodosUseCase,
                    getTodoByIdUseCase,
                    addTodoUseCase,
                    updateTodoUseCase,
                    deleteTodoUseCase
                )

                // When
                viewModel.getTodoById(todoId)
                testDispatcher.scheduler.advanceUntilIdle()

                // Then
                viewModel.uiState.test {
                    val state = awaitItem()
                    state.error shouldBe "Todo not found"
                }
            }
        }
    }
})
