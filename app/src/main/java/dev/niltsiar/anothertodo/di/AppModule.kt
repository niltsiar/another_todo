package dev.niltsiar.anothertodo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.niltsiar.anothertodo.data.local.TodoDao
import dev.niltsiar.anothertodo.data.local.TodoDatabase
import dev.niltsiar.anothertodo.data.repository.TodoRepositoryImpl
import dev.niltsiar.anothertodo.domain.repository.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(todoDao)
    }
}
