package dev.niltsiar.anothertodo.data.repository

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import dev.niltsiar.anothertodo.data.local.TodoDao
import dev.niltsiar.anothertodo.data.mapper.toDomain
import dev.niltsiar.anothertodo.data.model.Priority
import dev.niltsiar.anothertodo.data.model.TodoItem as DataTodoItem
import dev.niltsiar.anothertodo.domain.model.TodoItem as DomainTodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class TodoRepositoryImplTest : StringSpec({
    val todoDao = mockk<TodoDao>()
    val repository = TodoRepositoryImpl(todoDao)

    "getAllTodos should return mapped todos from dao" {
        checkAll(Arb.int(min = 0, max = 5)) { count ->
            runTest {
                // Given
                val dataTodos = List(count) { index ->
                    DataTodoItem(
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
                every { todoDao.getAllTodos() } returns flowOf(dataTodos)

                // When & Then
                repository.getAllTodos().test {
                    val result = awaitItem()
                    result.size shouldBe count

                    for (i in 0 until count) {
                        result[i].id shouldBe (i + 1).toLong()
                        result[i].title shouldBe "Test Todo ${i + 1}"
                        result[i].description shouldBe "Description for todo ${i + 1}"
                        result[i].isCompleted shouldBe (i % 2 == 0)
                    }

                    awaitComplete()
                }
            }
        }
    }

    "getTodoById should return Right with todo when found" {
        checkAll(
            Arb.long(min = 1),
            Arb.string(minSize = 1, maxSize = 100),
            Arb.string(minSize = 0, maxSize = 500),
            Arb.boolean(),
            Arb.enum<Priority>()
        ) { id, title, description, isCompleted, priority ->
            runTest {
                // Given
                val dataTodo = DataTodoItem(
                    id = id,
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    priority = priority
                )
                coEvery { todoDao.getTodoById(id) } returns dataTodo

                // When
                val result = repository.getTodoById(id)

                // Then
                result shouldBe dataTodo.toDomain().right()
            }
        }
    }

    "getTodoById should return Left with TodoNotFound when not found" {
        checkAll(Arb.long(min = 1)) { id ->
            runTest {
                // Given
                coEvery { todoDao.getTodoById(id) } returns null

                // When
                val result = repository.getTodoById(id)

                // Then
                result shouldBe TodoError.TodoNotFound.left()
            }
        }
    }

    "addTodo should return Right with id when successful" {
        checkAll(
            Arb.string(minSize = 1, maxSize = 100),
            Arb.string(minSize = 0, maxSize = 500),
            Arb.boolean(),
            Arb.enum<dev.niltsiar.anothertodo.domain.model.Priority>(),
            Arb.long(min = 1)
        ) { title, description, isCompleted, priority, expectedId ->
            runTest {
                // Given
                val domainTodo = DomainTodoItem(
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    priority = priority
                )
                coEvery { todoDao.insertTodo(any()) } returns expectedId

                // When
                val result = repository.addTodo(domainTodo)

                // Then
                result shouldBe expectedId.right()
                coVerify { todoDao.insertTodo(any()) }
            }
        }
    }

    "addTodo should return Left with DatabaseError when exception occurs" {
        checkAll(
            Arb.string(minSize = 1, maxSize = 100),
            Arb.string(minSize = 0, maxSize = 500)
        ) { title, description ->
            runTest {
                // Given
                val domainTodo = DomainTodoItem(
                    title = title,
                    description = description
                )
                coEvery { todoDao.insertTodo(any()) } throws RuntimeException("Database error")

                // When
                val result = repository.addTodo(domainTodo)

                // Then
                result shouldBe TodoError.DatabaseError.left()
            }
        }
    }
})
