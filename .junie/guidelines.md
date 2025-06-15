* This is an Android application. Use Kotlin and coroutines.

* Use clean architecture principles and MVVM patterns.
* Follow SOLID principles and Google's Android app architecture guidelines.
* Use different modules for different layers of clean architecture.
* Use Jetpack Compose for the UI.
* Use Material 3
* Use Compose navigation, using serializable data destinations with Kotlinx serialization.
* Use Hilt for dependency injection.
* Use multiplatform libraries where applicable, like kotlinx.serialization,
  kotlinx.datetime...
* Use arrow-kt for functional programming patterns, specifically for error handling
  and data transformations using Either as return types when possible.
* Use gitmoji heavily for commit messages using emojis and conventional commits.
* Create a commit using git for every change in the files.
* Build the app on every step to be sure it works using the gradle task "build"
* Create unit tests for each feature.
* Use the latest versions of libraries and tools, even if they are in alpha or beta.
* Do not use BOM versions for dependencies. Use individual versions for each library.
* Use libs.versions.toml for dependency versions and the plugins block with alias
  for plugins in the build.gradle.kts file.
* Do not pollute code with @Inject for hilt. Create the necessary modules in the di package
  and use @Binds or @Provides to provide dependencies.
* For compose, use ImmutableList from kotlinx when possible.
* Using kotest, create tests using property based testing and test
  the business logic of the app.
* Instead of sealed classes use sealed interfaces when possible for sealed hierarchies.
* Include compose previews for all composables.
