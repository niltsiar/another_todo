package dev.niltsiar.anothertodo.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import dev.niltsiar.anothertodo.domain.usecase.AddTodoUseCase
import dev.niltsiar.anothertodo.domain.usecase.DeleteTodoUseCase
import dev.niltsiar.anothertodo.domain.usecase.GetTodoByIdUseCase
import dev.niltsiar.anothertodo.domain.usecase.GetTodosUseCase
import dev.niltsiar.anothertodo.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideGetTodosUseCase(todoRepository: TodoRepository): GetTodosUseCase {
        return GetTodosUseCase(todoRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTodoByIdUseCase(todoRepository: TodoRepository): GetTodoByIdUseCase {
        return GetTodoByIdUseCase(todoRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddTodoUseCase(todoRepository: TodoRepository): AddTodoUseCase {
        return AddTodoUseCase(todoRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateTodoUseCase(todoRepository: TodoRepository): UpdateTodoUseCase {
        return UpdateTodoUseCase(todoRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTodoUseCase(todoRepository: TodoRepository): DeleteTodoUseCase {
        return DeleteTodoUseCase(todoRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideViewModelScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    }
}
