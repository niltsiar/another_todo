package dev.niltsiar.anothertodo.data.mapper

import dev.niltsiar.anothertodo.data.model.TodoItem as DataTodoItem
import dev.niltsiar.anothertodo.data.model.Priority as DataPriority
import dev.niltsiar.anothertodo.domain.model.TodoItem as DomainTodoItem
import dev.niltsiar.anothertodo.domain.model.Priority as DomainPriority

// Extension functions to map between domain and data models
fun DomainTodoItem.toData(): DataTodoItem {
    return DataTodoItem(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        completedAt = completedAt,
        dueDate = dueDate,
        priority = when (priority) {
            DomainPriority.LOW -> DataPriority.LOW
            DomainPriority.MEDIUM -> DataPriority.MEDIUM
            DomainPriority.HIGH -> DataPriority.HIGH
        }
    )
}

fun DataTodoItem.toDomain(): DomainTodoItem {
    return DomainTodoItem(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        completedAt = completedAt,
        dueDate = dueDate,
        priority = when (priority) {
            DataPriority.LOW -> DomainPriority.LOW
            DataPriority.MEDIUM -> DomainPriority.MEDIUM
            DataPriority.HIGH -> DomainPriority.HIGH
        }
    )
}

fun List<DataTodoItem>.toDomain(): List<DomainTodoItem> {
    return map { it.toDomain() }
}
