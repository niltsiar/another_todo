package dev.niltsiar.anothertodo.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.niltsiar.anothertodo.domain.model.Priority
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.presentation.viewmodel.TodoViewModel

@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    TodoListScreenContent(
        isLoading = false,
        todos = listOf(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onAddTodo: () -> Unit,
    onTodoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    TodoListScreenContent(
        isLoading = uiState.isLoading,
        todos = uiState.todos,
        onAddTodo = onAddTodo,
        onTodoClick = onTodoClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreenContent(
    isLoading: Boolean,
    todos: List<TodoItem>,
    onAddTodo: () -> Unit,
    onTodoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTodo) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.todos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No todos yet. Add one by clicking the + button.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(uiState.todos) { todo ->
                    TodoItem(
                        todo = todo,
                        onTodoClick = { onTodoClick(todo.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    val todo = TodoItem(
        id = 1,
        title = "Sample Todo",
        description = "This is a sample todo item for preview",
        isCompleted = false,
        priority = Priority.MEDIUM
    )
    TodoItem(
        todo = todo,
        onTodoClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    MaterialTheme {
        TodoItem(
            todo = TodoItem(
                id = 1,
                title = "Sample Todo",
                description = "This is a sample todo item for preview",
                isCompleted = false,
                priority = Priority.MEDIUM
            ),
            onTodoClick = {}
        )
    }
}

@Composable
fun TodoItem(
    todo: TodoItem,
    onTodoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onTodoClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (todo.isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (todo.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    MaterialTheme {
        val sampleTodos = listOf(
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
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(sampleTodos) { todo ->
                    TodoItem(
                        todo = todo,
                        onTodoClick = {}
                    )
                }
            }
        }
    }
}
