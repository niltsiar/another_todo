package dev.niltsiar.anothertodo.presentation.model

import dev.niltsiar.anothertodo.domain.model.TodoItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TodoUiState(
    val todos: ImmutableList<TodoItem> = persistentListOf(),
    val selectedTodo: TodoItem? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
