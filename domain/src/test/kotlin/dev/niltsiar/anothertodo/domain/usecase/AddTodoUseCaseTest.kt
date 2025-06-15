package dev.niltsiar.anothertodo.domain.usecase

import arrow.core.left
import arrow.core.right
import dev.niltsiar.anothertodo.domain.model.Priority
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class AddTodoUseCaseTest : StringSpec({
    val repository = mockk<TodoRepository>()
    val useCase = AddTodoUseCase(repository)

    "should return Right with id when repository returns success" {
        runTest {
            // Given
            val todo = TodoItem(
                title = "Test Todo",
                description = "Test Description",
                isCompleted = false,
                priority = Priority.MEDIUM
            )
            val expectedId = 1L
            coEvery { repository.addTodo(todo) } returns expectedId.right()

            // When
            val result = useCase(todo)

            // Then
            result shouldBe expectedId.right()
        }
    }

    "should return Left with error when repository returns error" {
        runTest {
            // Given
            val todo = TodoItem(
                title = "Test Todo",
                description = "Test Description",
                isCompleted = false,
                priority = Priority.MEDIUM
            )
            coEvery { repository.addTodo(todo) } returns TodoError.DatabaseError.left()

            // When
            val result = useCase(todo)

            // Then
            result shouldBe TodoError.DatabaseError.left()
        }
    }
})
