package dev.niltsiar.anothertodo.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.niltsiar.anothertodo.presentation.ui.navigation.TodoDestinations
import dev.niltsiar.anothertodo.presentation.ui.screens.TodoListScreen
import dev.niltsiar.anothertodo.presentation.ui.screens.AddEditTodoScreen

@Composable
fun TodoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
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
                    }
                )
            }
            composable(
                route = TodoDestinations.AddEditTodo.route,
                arguments = TodoDestinations.AddEditTodo.arguments
            ) { backStackEntry ->
                val todoIdString = backStackEntry.arguments?.getString("todoId")
                val todoId = if (todoIdString == "new") null else todoIdString?.toLongOrNull()
                AddEditTodoScreen(
                    todoId = todoId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
