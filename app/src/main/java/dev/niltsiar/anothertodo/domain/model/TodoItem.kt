package dev.niltsiar.anothertodo.domain.model

import java.time.LocalDateTime

data class TodoItem(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val completedAt: LocalDateTime? = null,
    val dueDate: LocalDateTime? = null,
    val priority: Priority = Priority.MEDIUM,
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

// Extension functions to map between domain and data models
fun TodoItem.toData(): dev.niltsiar.anothertodo.data.model.TodoItem {
    return dev.niltsiar.anothertodo.data.model.TodoItem(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        completedAt = completedAt,
        dueDate = dueDate,
        priority = when (priority) {
            Priority.LOW -> dev.niltsiar.anothertodo.data.model.Priority.LOW
            Priority.MEDIUM -> dev.niltsiar.anothertodo.data.model.Priority.MEDIUM
            Priority.HIGH -> dev.niltsiar.anothertodo.data.model.Priority.HIGH
        }
    )
}

fun dev.niltsiar.anothertodo.data.model.TodoItem.toDomain(): TodoItem {
    return TodoItem(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        completedAt = completedAt,
        dueDate = dueDate,
        priority = when (priority) {
            dev.niltsiar.anothertodo.data.model.Priority.LOW -> Priority.LOW
            dev.niltsiar.anothertodo.data.model.Priority.MEDIUM -> Priority.MEDIUM
            dev.niltsiar.anothertodo.data.model.Priority.HIGH -> Priority.HIGH
        }
    )
}

fun List<dev.niltsiar.anothertodo.data.model.TodoItem>.toDomain(): List<TodoItem> {
    return map { it.toDomain() }
}
