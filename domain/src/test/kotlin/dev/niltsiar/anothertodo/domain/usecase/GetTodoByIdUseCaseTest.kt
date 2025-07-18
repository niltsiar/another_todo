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

class GetTodoByIdUseCaseTest {

    private lateinit var repository: TodoRepository
    private lateinit var useCase: GetTodoByIdUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTodoByIdUseCase(repository)
    }

    @Test
    fun `should return Right with todo when repository returns success`() = runTest {
        // Given
        val todoId = 1L
        val expectedTodo = TodoItem(
            id = todoId,
            title = "Test Todo",
            description = "Test Description",
            isCompleted = false,
            priority = Priority.MEDIUM
        )
        coEvery { repository.getTodoById(todoId) } returns expectedTodo.right()

        // When
        val result = useCase(todoId)

        // Then
        result shouldBe expectedTodo.right()
    }

    @Test
    fun `should return Left with error when repository returns error`() = runTest {
        // Given
        val todoId = 1L
        coEvery { repository.getTodoById(todoId) } returns TodoError.TodoNotFound.left()

        // When
        val result = useCase(todoId)

        // Then
        result shouldBe TodoError.TodoNotFound.left()
    }
}
