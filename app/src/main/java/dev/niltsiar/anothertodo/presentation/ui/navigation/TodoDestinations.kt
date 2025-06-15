package dev.niltsiar.anothertodo.presentation.ui.navigation

import android.R.attr.type
import androidx.navigation.NavType
import androidx.navigation.navArgument

object TodoDestinations {
    const val TODO_LIST = "todo_list"
    const val ADD_EDIT_TODO = "add_edit_todo"

    val arguments = listOf(
        navArgument("todoId") {
            type = NavType.StringType
            nullable = true
        }
    )
}
