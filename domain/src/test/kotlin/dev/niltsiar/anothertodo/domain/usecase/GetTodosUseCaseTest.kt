package dev.niltsiar.anothertodo.domain.usecase

import app.cash.turbine.test
import dev.niltsiar.anothertodo.domain.model.Priority
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class GetTodosUseCaseTest : StringSpec({
    val repository = mockk<TodoRepository>()
    val getTodosUseCase = GetTodosUseCase(repository)

    "invoke should return todos from repository" {
        checkAll(Arb.int(min = 0, max = 5)) { count ->
            runTest {
                // Given
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
                every { repository.getAllTodos() } returns flowOf(todos)

                // When & Then
                getTodosUseCase().test {
                    val result = awaitItem()
                    result shouldBe todos
                    awaitComplete()
                }
            }
        }
    }

    "getActiveTodos should return active todos from repository" {
        checkAll(Arb.int(min = 0, max = 5)) { count ->
            runTest {
                // Given
                val activeTodos = List(count) { index ->
                    TodoItem(
                        id = (index + 1).toLong(),
                        title = "Active Todo ${index + 1}",
                        description = "Description for active todo ${index + 1}",
                        isCompleted = false,
                        priority = when (index % 3) {
                            0 -> Priority.LOW
                            1 -> Priority.MEDIUM
                            else -> Priority.HIGH
                        }
                    )
                }
                every { repository.getActiveTodos() } returns flowOf(activeTodos)

                // When & Then
                getTodosUseCase.getActiveTodos().test {
                    val result = awaitItem()
                    result shouldBe activeTodos
                    awaitComplete()
                }
            }
        }
    }

    "getCompletedTodos should return completed todos from repository" {
        checkAll(Arb.int(min = 0, max = 5)) { count ->
            runTest {
                // Given
                val completedTodos = List(count) { index ->
                    TodoItem(
                        id = (index + 1).toLong(),
                        title = "Completed Todo ${index + 1}",
                        description = "Description for completed todo ${index + 1}",
                        isCompleted = true,
                        priority = when (index % 3) {
                            0 -> Priority.LOW
                            1 -> Priority.MEDIUM
                            else -> Priority.HIGH
                        }
                    )
                }
                every { repository.getCompletedTodos() } returns flowOf(completedTodos)

                // When & Then
                getTodosUseCase.getCompletedTodos().test {
                    val result = awaitItem()
                    result shouldBe completedTodos
                    awaitComplete()
                }
            }
        }
    }
})
