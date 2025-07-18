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

class AddTodoUseCaseTest : StringSpec({
    val repository = mockk<TodoRepository>()
    val useCase = AddTodoUseCase(repository)

    "should return Right with id when repository returns success" {
        checkAll(
            Arb.string(minSize = 1, maxSize = 100),
            Arb.string(minSize = 0, maxSize = 500),
            Arb.boolean(),
            Arb.enum<Priority>(),
            Arb.long(min = 1)
        ) { title, description, isCompleted, priority, expectedId ->
            runTest {
                // Given
                val todo = TodoItem(
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    priority = priority
                )
                coEvery { repository.addTodo(todo) } returns expectedId.right()

                // When
                val result = useCase(todo)

                // Then
                result shouldBe expectedId.right()
            }
        }
    }

    "should return Left with error when repository returns error" {
        checkAll(
            Arb.string(minSize = 1, maxSize = 100),
            Arb.string(minSize = 0, maxSize = 500),
            Arb.boolean(),
            Arb.enum<Priority>()
        ) { title, description, isCompleted, priority ->
            runTest {
                // Given
                val todo = TodoItem(
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    priority = priority
                )
                coEvery { repository.addTodo(todo) } returns TodoError.DatabaseError.left()

                // When
                val result = useCase(todo)

                // Then
                result shouldBe TodoError.DatabaseError.left()
            }
        }
    }
})
