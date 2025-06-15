package dev.niltsiar.anothertodo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.niltsiar.anothertodo.domain.model.TodoItem
import dev.niltsiar.anothertodo.domain.repository.TodoError
import dev.niltsiar.anothertodo.domain.usecase.AddTodoUseCase
import dev.niltsiar.anothertodo.domain.usecase.DeleteTodoUseCase
import dev.niltsiar.anothertodo.domain.usecase.GetTodoByIdUseCase
import dev.niltsiar.anothertodo.domain.usecase.GetTodosUseCase
import dev.niltsiar.anothertodo.domain.usecase.UpdateTodoUseCase
import dev.niltsiar.anothertodo.presentation.model.TodoUiState
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getTodosUseCase().collect { todos ->
                _uiState.update { it.copy(todos = todos, isLoading = false) }
            }
        }
    }

    fun loadActiveTodos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getTodosUseCase.getActiveTodos().collect { todos ->
                _uiState.update { it.copy(todos = todos, isLoading = false) }
            }
        }
    }

    fun loadCompletedTodos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getTodosUseCase.getCompletedTodos().collect { todos ->
                _uiState.update { it.copy(todos = todos, isLoading = false) }
            }
        }
    }

    fun getTodoById(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getTodoByIdUseCase(id).fold(
                { error -> handleError(error) },
                { todo -> _uiState.update { it.copy(selectedTodo = todo, isLoading = false) } }
            )
        }
    }

    fun addTodo(todo: TodoItem) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            addTodoUseCase(todo).fold(
                { error -> handleError(error) },
                { _ -> loadTodos() }
            )
        }
    }

    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            updateTodoUseCase(todo).fold(
                { error -> handleError(error) },
                { _ -> loadTodos() }
            )
        }
    }

    fun deleteTodo(todo: TodoItem) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            deleteTodoUseCase(todo).fold(
                { error -> handleError(error) },
                { _ -> loadTodos() }
            )
        }
    }

    fun deleteTodoById(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            deleteTodoUseCase.deleteById(id).fold(
                { error -> handleError(error) },
                { _ -> loadTodos() }
            )
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            deleteTodoUseCase.deleteAll().fold(
                { error -> handleError(error) },
                { _ -> loadTodos() }
            )
        }
    }

    private fun handleError(error: TodoError) {
        _uiState.update {
            it.copy(
                error = when (error) {
                    is TodoError.DatabaseError -> "Database error"
                    is TodoError.TodoNotFound -> "Todo not found"
                    is TodoError.UnknownError -> "Unknown error: ${error.message}"
                },
                isLoading = false
            )
        }
    }
}
