package dev.niltsiar.anothertodo.presentation.model

import dev.niltsiar.anothertodo.domain.model.TodoItem

data class TodoUiState(
    val todos: List<TodoItem> = emptyList(),
    val selectedTodo: TodoItem? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
