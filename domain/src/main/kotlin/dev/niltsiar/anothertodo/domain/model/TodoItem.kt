package dev.niltsiar.anothertodo.domain.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
data class TodoItem(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val updatedAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val completedAt: LocalDateTime? = null,
    val dueDate: LocalDateTime? = null,
    val priority: Priority = Priority.MEDIUM,
)

enum class Priority {
    LOW, MEDIUM, HIGH
}
