package dev.niltsiar.anothertodo.presentation.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.niltsiar.anothertodo.domain.model.Priority
import dev.niltsiar.anothertodo.domain.model.TodoItem
import kotlinx.collections.immutable.persistentListOf

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

// Previews
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
fun TodoItemWithThemePreview() {
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

@Preview(showBackground = true)
@Composable
fun TodoListItemsPreview() {
    MaterialTheme {
        val sampleTodos = persistentListOf(
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

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sampleTodos) { todo ->
                TodoItem(
                    todo = todo,
                    onTodoClick = {}
                )
            }
        }
    }
}
