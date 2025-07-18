package dev.niltsiar.anothertodo.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.niltsiar.anothertodo.domain.model.Priority
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.presentation.ui.components.TodoItem
import dev.niltsiar.anothertodo.presentation.ui.theme.AnotherTodoTheme
import dev.niltsiar.anothertodo.presentation.viewmodel.TodoViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList


@Composable
fun TodoListScreen(
    onAddTodo: () -> Unit,
    onTodoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState? = null,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val localSnackbarHostState = remember { SnackbarHostState() }
    val effectiveSnackbarHostState = snackbarHostState ?: localSnackbarHostState

    // Load todos when the screen is first composed
    LaunchedEffect(Unit) {
        viewModel.loadTodos()
    }

    // Show error in snackbar if present
    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            effectiveSnackbarHostState.showSnackbar(message = uiState.error!!)
        }
    }

    TodoListScreenContent(
        isLoading = uiState.isLoading,
        todos = uiState.todos.toImmutableList(),
        onAddTodo = onAddTodo,
        onTodoClick = onTodoClick,
        modifier = modifier
    )
}

@Composable
fun TodoListScreenContent(
    isLoading: Boolean,
    todos: ImmutableList<TodoItem>,
    onAddTodo: () -> Unit,
    onTodoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (todos.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No todos yet. Add one by clicking the + button.")
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(todos) { todo ->
                TodoItem(
                    todo = todo,
                    onTodoClick = { onTodoClick(todo.id) }
                )
            }
        }
    }
}

// Previews
@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    AnotherTodoTheme {
        TodoListScreenContent(
            isLoading = false,
            todos = persistentListOf(
                TodoItem(
                    id = 1,
                    title = "Sample Todo 1",
                    description = "This is a sample todo item for preview",
                    isCompleted = false,
                    priority = Priority.HIGH
                ),
                TodoItem(
                    id = 2,
                    title = "Sample Todo 2",
                    description = "This is another sample todo item",
                    isCompleted = true,
                    priority = Priority.LOW
                )
            ),
            onAddTodo = {},
            onTodoClick = {}
        )
    }
}
