package dev.niltsiar.anothertodo.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.presentation.ui.theme.AnotherTodoTheme
import dev.niltsiar.anothertodo.presentation.viewmodel.TodoViewModel

@Composable
fun AddEditTodoScreen(
    onNavigateBack: () -> Unit,
    todoId: Long? = null,
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState? = null,
    onSave: ((TodoItem) -> Unit)? = null,
    saveTrigger: Boolean = false
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val localSnackbarHostState = remember { SnackbarHostState() }
    val effectiveSnackbarHostState = snackbarHostState ?: localSnackbarHostState

    // Local state for the form
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }

    // If editing an existing todo, load its data
    LaunchedEffect(todoId) {
        if (todoId != null) {
            viewModel.getTodoById(todoId)
        }
    }

    // Update local state when selected todo changes
    LaunchedEffect(uiState.selectedTodo) {
        uiState.selectedTodo?.let { todo ->
            title = todo.title
            description = todo.description
            isCompleted = todo.isCompleted
        }
    }

    // Show error in snackbar if present
    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            effectiveSnackbarHostState.showSnackbar(message = uiState.error!!)
        }
    }

    // Function to handle save action
    val handleSave = {
        if (title.isNotBlank()) {
            val todo = TodoItem(
                id = todoId ?: 0,
                title = title,
                description = description,
                isCompleted = isCompleted
            )

            if (todoId == null) {
                viewModel.addTodo(todo)
            } else {
                viewModel.updateTodo(todo)
            }

            onSave?.invoke(todo)
            onNavigateBack()
        }
    }

    // Observe saveTrigger and perform save action when it changes
    LaunchedEffect(saveTrigger) {
        if (saveTrigger) {
            handleSave()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { isCompleted = !isCompleted },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Checkbox(
                checked = isCompleted,
                onCheckedChange = { isCompleted = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Completed")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditTodoFormPreview() {
    AnotherTodoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = "Sample Todo Title",
                onValueChange = {},
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "This is a sample todo description for preview",
                onValueChange = {},
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.Start)
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {}
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Completed")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AddEditTodoScreenPreview() {
    AnotherTodoTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Add Todo") },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = "Sample Todo Title",
                    onValueChange = {},
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "This is a sample todo description for preview",
                    onValueChange = {},
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {}
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Completed")
                }
            }
        }
    }
}
