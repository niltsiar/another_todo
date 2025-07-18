package dev.niltsiar.anothertodo.domain.usecase

import arrow.core.left
import arrow.core.right
import dev.niltsiar.anothertodo.domain.model.Priority
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateTodoUseCaseTest {

    private lateinit var repository: TodoRepository
    private lateinit var useCase: UpdateTodoUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpdateTodoUseCase(repository)
    }

    @Test
    fun `should return Right with Unit when repository returns success`() = runTest {
        // Given
        val todo = TodoItem(
            id = 1L,
            title = "Test Todo",
            description = "Test Description",
            isCompleted = false,
            priority = Priority.MEDIUM
        )
        coEvery { repository.updateTodo(todo) } returns Unit.right()

        // When
        val result = useCase(todo)

        // Then
        result shouldBe Unit.right()
    }

    @Test
    fun `should return Left with error when repository returns error`() = runTest {
        // Given
        val todo = TodoItem(
            id = 1L,
            title = "Test Todo",
            description = "Test Description",
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
