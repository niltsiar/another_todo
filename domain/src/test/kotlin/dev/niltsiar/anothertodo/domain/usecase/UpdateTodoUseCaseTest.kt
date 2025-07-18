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
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime

class UpdateTodoUseCaseTest : StringSpec({
    val repository = mockk<TodoRepository>()
    val useCase = UpdateTodoUseCase(repository)

    "should return Right with Unit when repository returns success" {
        checkAll(
            Arb.long(min = 1),
            Arb.string(minSize = 1, maxSize = 100),
            Arb.string(minSize = 0, maxSize = 500),
            Arb.boolean(),
            Arb.enum<Priority>()
        ) { id, title, description, isCompleted, priority ->
            runTest {
                // Given
                val todo = TodoItem(
                    id = id,
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    priority = priority
                )
                coEvery { repository.updateTodo(todo) } returns Unit.right()

                // When
                val result = useCase(todo)

                // Then
                result shouldBe Unit.right()
            }
        }
    }

    "should return Left with TodoNotFound when repository returns error for non-existent todo" {
        checkAll(
            Arb.long(min = 1),
            Arb.string(minSize = 1, maxSize = 100),
            Arb.string(minSize = 0, maxSize = 500)
        ) { id, title, description ->
            runTest {
                // Given
                val todo = TodoItem(
                    id = id,
                    title = title,
                    description = description,
                    isCompleted = false,
                    priority = Priority.MEDIUM
                )
                coEvery { repository.updateTodo(todo) } returns TodoError.TodoNotFound.left()

                // When
                val result = useCase(todo)

                // Then
                result shouldBe TodoError.TodoNotFound.left()
            }
        }
    }

    "should return Left with DatabaseError when repository encounters database issues" {
        checkAll(
            Arb.long(min = 1),
            Arb.string(minSize = 1, maxSize = 100)
        ) { id, title ->
            runTest {
                // Given
                val todo = TodoItem(
                    id = id,
                    title = title,
                    description = "Test Description",
                    isCompleted = true,
                    priority = Priority.HIGH
                )
                coEvery { repository.updateTodo(todo) } returns TodoError.DatabaseError.left()

                // When
                val result = useCase(todo)

                // Then
                result shouldBe TodoError.DatabaseError.left()
            }
        }
    }

    "should handle todos with various completion states correctly" {
        checkAll(
            Arb.long(min = 1),
            Arb.string(minSize = 1, maxSize = 100),
            Arb.boolean()
        ) { id, title, isCompleted ->
            runTest {
                // Given
                val todo = TodoItem(
                    id = id,
                    title = title,
                    description = "Description for $title",
                    isCompleted = isCompleted,
                    priority = if (isCompleted) Priority.LOW else Priority.HIGH
                )
                coEvery { repository.updateTodo(todo) } returns Unit.right()

                // When
                val result = useCase(todo)

                // Then
                result shouldBe Unit.right()
            }
        }
    }
})
