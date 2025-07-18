package dev.niltsiar.anothertodo.presentation.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable

sealed interface TodoDestination {
    val route: String
}

object TodoDestinations {
    const val TODO_LIST_ROUTE = "todo_list"
    const val ADD_EDIT_TODO_ROUTE = "add_edit_todo"

    object TodoList : TodoDestination {
        override val route = TODO_LIST_ROUTE
    }

    @Serializable
    data class AddEditTodo(val todoId: Long? = null) : TodoDestination {
        override val route = "$ADD_EDIT_TODO_ROUTE/${todoId ?: "new"}"

        companion object {
            const val route = "$ADD_EDIT_TODO_ROUTE/{todoId}"
            val arguments = listOf(
                navArgument("todoId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        }
    }
}
