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
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class DeleteTodoUseCaseTest : StringSpec({
    val repository = mockk<TodoRepository>()
    val useCase = DeleteTodoUseCase(repository)

    "invoke should return Right with Unit when repository returns success" {
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
                coEvery { repository.deleteTodo(todo) } returns Unit.right()

                // When
                val result = useCase(todo)

                // Then
                result shouldBe Unit.right()
            }
        }
    }

    "invoke should return Left with error when repository returns error" {
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
                coEvery { repository.deleteTodo(todo) } returns TodoError.TodoNotFound.left()

                // When
                val result = useCase(todo)

                // Then
                result shouldBe TodoError.TodoNotFound.left()
            }
        }
    }

    "deleteById should return Right with Unit when repository returns success" {
        checkAll(Arb.long(min = 1)) { todoId ->
            runTest {
                // Given
                coEvery { repository.deleteTodoById(todoId) } returns Unit.right()

                // When
                val result = useCase.deleteById(todoId)

                // Then
                result shouldBe Unit.right()
            }
        }
    }

    "deleteById should return Left with error when repository returns error" {
        checkAll(Arb.long(min = 1)) { todoId ->
            runTest {
                // Given
                coEvery { repository.deleteTodoById(todoId) } returns TodoError.TodoNotFound.left()

                // When
                val result = useCase.deleteById(todoId)

                // Then
                result shouldBe TodoError.TodoNotFound.left()
            }
        }
    }

    "deleteAll should return Right with Unit when repository returns success" {
        runTest {
            // Given
            coEvery { repository.deleteAllTodos() } returns Unit.right()

            // When
            val result = useCase.deleteAll()

            // Then
            result shouldBe Unit.right()
        }
    }

    "deleteAll should return Left with error when repository returns error" {
        runTest {
            // Given
            coEvery { repository.deleteAllTodos() } returns TodoError.DatabaseError.left()

            // When
            val result = useCase.deleteAll()

            // Then
            result shouldBe TodoError.DatabaseError.left()
        }
    }
})
