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

class DeleteTodoUseCaseTest {

    private lateinit var repository: TodoRepository
    private lateinit var useCase: DeleteTodoUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = DeleteTodoUseCase(repository)
    }

    @Test
    fun `invoke should return Right with Unit when repository returns success`() = runTest {
        // Given
        val todo = TodoItem(
            id = 1L,
            title = "Test Todo",
            description = "Test Description",
            isCompleted = false,
            priority = Priority.MEDIUM
        )
        coEvery { repository.deleteTodo(todo) } returns Unit.right()

        // When
        val result = useCase(todo)

        // Then
        result shouldBe Unit.right()
    }

    @Test
    fun `invoke should return Left with error when repository returns error`() = runTest {
        // Given
        val todo = TodoItem(
            id = 1L,
            title = "Test Todo",
            description = "Test Description",
            isCompleted = false,
            priority = Priority.MEDIUM
        )
        coEvery { repository.deleteTodo(todo) } returns TodoError.TodoNotFound.left()

        // When
        val result = useCase(todo)

        // Then
        result shouldBe TodoError.TodoNotFound.left()
    }

    @Test
    fun `deleteById should return Right with Unit when repository returns success`() = runTest {
        // Given
        val todoId = 1L
        coEvery { repository.deleteTodoById(todoId) } returns Unit.right()

        // When
        val result = useCase.deleteById(todoId)

        // Then
        result shouldBe Unit.right()
    }

    @Test
    fun `deleteById should return Left with error when repository returns error`() = runTest {
        // Given
        val todoId = 1L
        coEvery { repository.deleteTodoById(todoId) } returns TodoError.TodoNotFound.left()

        // When
        val result = useCase.deleteById(todoId)

        // Then
        result shouldBe TodoError.TodoNotFound.left()
    }

    @Test
    fun `deleteAll should return Right with Unit when repository returns success`() = runTest {
        // Given
        coEvery { repository.deleteAllTodos() } returns Unit.right()

        // When
        val result = useCase.deleteAll()

        // Then
        result shouldBe Unit.right()
    }

    @Test
    fun `deleteAll should return Left with error when repository returns error`() = runTest {
        // Given
        coEvery { repository.deleteAllTodos() } returns TodoError.DatabaseError.left()

        // When
        val result = useCase.deleteAll()

        // Then
        result shouldBe TodoError.DatabaseError.left()
    }
}
