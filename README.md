# New Android Jetpack Compose

## Description

This project is a modern Android application built using Jetpack Compose, Kotlin, and following the MVVM (Model-View-ViewModel) architecture. It demonstrates the use of various Android development technologies and best practices.

## Features

- Jetpack Compose UI
- MVVM Architecture
- Dependency Injection with Hilt
- Navigation using Jetpack Navigation Component
- Retrofit for network requests
- Coroutines and Flow for asynchronous programming

## Prerequisites

- Android Studio Arctic Fox or newer
- Kotlin 1.5.0 or newer
- JDK 11
- Android SDK with a minimum API level of 26

## Getting Started

1. Clone the repository:

   ```
   git clone https://github.com/linhnguyen-gt/NewAndroidJetpackCompose.git
   ```

2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or physical device.

## To run ktlint, you can use the following Gradle commands

- To check your code: ./gradlew ktlintCheck
- To format your code: ./gradlew ktlintFormat

## Project Structure

- `app/src/main/java/com/newAndroid/newandroidjetpackcompose/`
  - `data/`: Data layer
    - `local/`: Local storage and preferences
    - `remote/`: Remote data sources
      - `api/`: API interfaces
      - `model/`: API Response Models
      - `retrofit/`: Retrofit configuration
    - `repository/`: Repository implementations
  - `domain/`: Domain layer
    - `interfaces/`: Interface definitions
    - `usecase/`: Use cases for business logic
  - `presentation/`: Presentation layer
    - `common/`: Common UI components
    - `navigation/`: Navigation-related classes
    - `ui/`: UI components
      - `screens/`: Screen Composables
      - `theme/`: App theme and styling
    - `viewmodel/`: ViewModels for screens
  - `util/`: Utility classes and extensions

## Dependencies

- Jetpack Compose
- Hilt for dependency injection
- Retrofit for network requests
- Kotlin Coroutines and Flow
- Navigation Compose

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the [Apache License 2.0](LICENSE).
