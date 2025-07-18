package dev.niltsiar.anothertodo.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.presentation.ui.navigation.TodoDestinations
import dev.niltsiar.anothertodo.presentation.ui.screens.TodoListScreen
import dev.niltsiar.anothertodo.presentation.ui.screens.AddEditTodoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: TodoDestinations.TodoList.route

    // Determine if we're on the AddEditTodo screen
    val isAddEditScreen = currentRoute.startsWith(TodoDestinations.ADD_EDIT_TODO_ROUTE)

    // Get todoId from route if we're on the AddEditTodo screen
    val todoIdString = navBackStackEntry?.arguments?.getString("todoId")
    val isEditingTodo = todoIdString != null && todoIdString != "new"

    // State for the save action in AddEditTodoScreen
    var currentTodo by remember { mutableStateOf<TodoItem?>(null) }

    // State for triggering save in AddEditTodoScreen
    var saveTrigger by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        when {
                            isAddEditScreen && isEditingTodo -> "Edit Todo"
                            isAddEditScreen -> "Add Todo"
                            else -> "Todo List"
                        }
                    ) 
                },
                navigationIcon = {
                    if (isAddEditScreen) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (!isAddEditScreen) {
                // Add Todo FAB for TodoListScreen
                FloatingActionButton(onClick = { 
                    navController.navigate(TodoDestinations.AddEditTodo().route) 
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Todo")
                }
            } else {
                // Save FAB for AddEditTodoScreen
                FloatingActionButton(onClick = {
                    // Toggle the trigger to notify the AddEditTodoScreen to save
                    saveTrigger = !saveTrigger
                }) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TodoDestinations.TodoList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TodoDestinations.TodoList.route) {
                TodoListScreen(
                    onAddTodo = { 
                        navController.navigate(TodoDestinations.AddEditTodo().route) 
                    },
                    onTodoClick = { todoId ->
                        navController.navigate(TodoDestinations.AddEditTodo(todoId).route)
                    },
                    snackbarHostState = snackbarHostState
                )
            }
            composable(
                route = TodoDestinations.AddEditTodo.route,
                arguments = TodoDestinations.AddEditTodo.arguments
            ) { backStackEntry ->
                val todoId = if (todoIdString == "new") null else todoIdString?.toLongOrNull()
                AddEditTodoScreen(
                    todoId = todoId,
                    onNavigateBack = { navController.popBackStack() },
                    snackbarHostState = snackbarHostState,
                    onSave = { todo ->
                        currentTodo = todo
                        navController.popBackStack()
                    },
                    saveTrigger = saveTrigger
                )
            }
        }
    }
}
