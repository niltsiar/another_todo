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
            startDestination = TodoDestinations.TODO_LIST,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TodoDestinations.TODO_LIST) {
                TodoListScreen(
                    onAddTodo = { navController.navigate(TodoDestinations.ADD_EDIT_TODO) },
                    onTodoClick = { todoId ->
                        navController.navigate("${TodoDestinations.ADD_EDIT_TODO}/$todoId")
                    }
                )
            }
            composable(
                route = "${TodoDestinations.ADD_EDIT_TODO}/{todoId}",
                arguments = TodoDestinations.arguments
            ) { backStackEntry ->
                val todoId = backStackEntry.arguments?.getString("todoId")?.toLongOrNull()
                AddEditTodoScreen(
                    todoId = todoId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(TodoDestinations.ADD_EDIT_TODO) {
                AddEditTodoScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
