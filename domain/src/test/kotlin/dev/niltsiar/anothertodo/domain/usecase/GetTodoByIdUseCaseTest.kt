package dev.niltsiar.anothertodo.domain.usecase

import arrow.core.left
import arrow.core.right
import dev.niltsiar.anothertodo.domain.model.Priority
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.positiveInts
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.kotest.property.forAll
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime

class GetTodoByIdUseCaseTest : StringSpec({
    val repository = mockk<TodoRepository>()
    val useCase = GetTodoByIdUseCase(repository)

    "should return Right with todo when repository returns success" {
        checkAll(Arb.long(min = 1)) { todoId ->
            runTest {
                // Given
                val expectedTodo = TodoItem(
                    id = todoId,
                    title = "Test Todo $todoId",
                    description = "Test Description for todo $todoId",
                    isCompleted = false,
                    priority = Priority.MEDIUM
                )
                coEvery { repository.getTodoById(todoId) } returns expectedTodo.right()

                // When
                val result = useCase(todoId)

                // Then
                result shouldBe expectedTodo.right()
            }
        }
    }

    "should return Left with TodoNotFound when repository returns error for non-existent todo" {
        forAll(Arb.long(min = 1)) { todoId ->
            runTest {
                // Given
                coEvery { repository.getTodoById(todoId) } returns TodoError.TodoNotFound.left()

                // When
                val result = useCase(todoId)

                // Then
                result shouldBe TodoError.TodoNotFound.left()
            }
            true
        }
    }

    "should return Left with DatabaseError when repository encounters database issues" {
        forAll(Arb.long(min = 1)) { todoId ->
            runTest {
                // Given
                coEvery { repository.getTodoById(todoId) } returns TodoError.DatabaseError.left()

                // When
                val result = useCase(todoId)

                // Then
                result shouldBe TodoError.DatabaseError.left()
            }
            true
        }
    }

    "should handle todos with various properties correctly" {
        checkAll(
            Arb.long(min = 1),
            Arb.string(minSize = 1, maxSize = 100),
            Arb.string(minSize = 0, maxSize = 500)
        ) { todoId, title, description ->
            runTest {
                // Given
                val expectedTodo = TodoItem(
                    id = todoId,
                    title = title,
                    description = description,
                    isCompleted = todoId % 2 == 0L, // Alternate between true and false
                    priority = when (todoId % 3) {
                        0L -> Priority.LOW
                        1L -> Priority.MEDIUM
                        else -> Priority.HIGH
                    }
                )
                coEvery { repository.getTodoById(todoId) } returns expectedTodo.right()

                // When
                val result = useCase(todoId)

                // Then
                result shouldBe expectedTodo.right()
            }
        }
    }
})
