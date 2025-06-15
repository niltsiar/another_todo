package dev.niltsiar.anothertodo.presentation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    // This module is empty because we're using the UseCaseModule to provide the use cases
    // and the TodoViewModel is using @HiltViewModel and @Inject to get its dependencies
}
