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

## Project Structure
- `app/src/main/java/com/newAndroid/newandroidjetpackcompose/`
  - `interfaces/`: Contains interface definitions
  - `models/`: Data models
  - `navigation/`: Navigation-related classes
  - `services/`: Service classes, including Retrofit services
  - `ui/`: UI-related classes and Compose UI components
  - `viewModels/`: ViewModels for each screen
  - `views/`: Compose screen definitions

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
