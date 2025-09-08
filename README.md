# CheqUpi Test - Google Sign-In Android App

A complete Android application built with MVVM Clean Architecture, Google Sign-In authentication, and Room database for user persistence.

## Architecture

This project follows Clean Architecture principles with three main layers:

### Domain Layer
- **User**: Domain entity representing a user with authentication data
- **AuthRepository**: Interface for authentication operations
- **Use Cases**: 
  - `GetCurrentUserUseCase`: Get current authenticated user
  - `SignInWithGoogleUseCase`: Handle Google Sign-In
  - `SignOutUseCase`: Handle user sign-out

### Data Layer
- **UserEntity**: Room database entity for user persistence
- **UserDao**: Data Access Object for user database operations
- **AuthRepositoryImpl**: Repository implementation with Google Sign-In
- **GoogleSignInHelper**: Helper class for Google Sign-In operations
- **UserMapper**: Mapping between entities and domain models

### Presentation Layer
- **AuthViewModel**: ViewModel with authentication state management
- **AuthUiState**: UI state representation (Loading, Authenticated, Unauthenticated, Error)
- **Screens**: 
  - `SplashScreen`: Launch screen with navigation logic
  - `LoginScreen`: Google Sign-In interface
  - `HomeScreen`: Main app content with navigation drawer
  - `ProfileScreen`: User profile display
- **NavigationDrawer**: Side navigation with profile and logout options

## Features

- ✅ MVVM Clean Architecture
- ✅ Google Sign-In Authentication
- ✅ Room Database for user persistence
- ✅ Jetpack Compose UI
- ✅ Navigation with Splash Screen
- ✅ State management (Loading, Success, Error)
- ✅ Material Design 3
- ✅ Dependency Injection with Hilt
- ✅ Kotlin Coroutines + Flow

## App Flow

### 1. Launch Flow
- **Splash Screen**: Checks if user is already signed in
  - If signed in → Navigate to Home Screen
  - If not signed in → Navigate to Login Screen

### 2. Authentication Flow
- **Login Screen**: 
  - Shows "Sign in with Google" button
  - On successful sign-in, saves user data to Room DB
  - Navigates to Home Screen

### 3. Main App Flow
- **Home Screen**: 
  - Displays dummy content
  - Has navigation drawer with profile and logout options
- **Profile Screen**: 
  - Shows user data from Google Sign-In (name, email, profile image)
  - Displays account information
- **Logout**: 
  - Clears Room DB
  - Signs out from Google
  - Redirects to Login Screen

## Setup Instructions

### 1. Firebase Configuration

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or use existing one
3. Add Android app with package name: `com.dev.chequpitest`
4. Download `google-services.json` and place it in `app/` directory
5. Enable Authentication in Firebase Console
6. Enable Google Sign-In provider

### 2. Google Sign-In Setup

1. In Firebase Console → Authentication → Sign-in method
2. Enable Google provider
3. Add your SHA-1 fingerprint for debug/release builds
4. Copy the Web Client ID from Firebase Console
5. Replace `YOUR_WEB_CLIENT_ID` in the code with your actual Web Client ID

### 3. Dependencies

The app uses the following key dependencies:
- **Room Database**: For local user persistence
- **Hilt**: For dependency injection
- **Jetpack Compose**: For modern UI
- **Navigation Compose**: For screen navigation
- **Firebase Auth**: For Google Sign-In
- **Coil**: For image loading

### 4. Build and Run

1. Sync the project with Gradle files
2. Build and run the application
3. The app will start with the splash screen and navigate based on authentication status

## Project Structure

```
app/src/main/java/com/dev/chequpitest/
├── domain/
│   ├── model/
│   │   └── User.kt
│   ├── repository/
│   │   └── AuthRepository.kt
│   └── usecase/
│       ├── GetCurrentUserUseCase.kt
│       ├── SignInWithGoogleUseCase.kt
│       └── SignOutUseCase.kt
├── data/
│   ├── auth/
│   │   └── GoogleSignInHelper.kt
│   ├── local/
│   │   ├── entity/
│   │   │   └── UserEntity.kt
│   │   ├── dao/
│   │   │   └── UserDao.kt
│   │   └── database/
│   │       └── AppDatabase.kt
│   ├── mapper/
│   │   └── UserMapper.kt
│   └── repository/
│       └── AuthRepositoryImpl.kt
├── presentation/
│   ├── navigation/
│   │   └── AppNavigation.kt
│   └── ui/
│       ├── state/
│       │   └── AuthUiState.kt
│       ├── viewmodel/
│       │   └── AuthViewModel.kt
│       ├── components/
│       │   └── NavigationDrawer.kt
│       └── screen/
│           ├── SplashScreen.kt
│           ├── LoginScreen.kt
│           ├── HomeScreen.kt
│           └── ProfileScreen.kt
├── di/
│   └── AppModule.kt
├── CheqUpiApplication.kt
└── MainActivity.kt
```

## Key Implementation Details

### Room Database
- Stores user authentication data locally
- Enables offline access to user information
- Automatically clears on logout

### Google Sign-In
- Uses Firebase Authentication
- Implements proper error handling
- Saves complete user profile data

### State Management
- Reactive UI with Flow and StateFlow
- Proper loading and error states
- Seamless navigation between screens

### Navigation
- Splash screen with authentication check
- Conditional navigation based on auth state
- Proper back stack management

## Testing

The project includes test structure for:
- Unit tests for use cases
- Repository tests
- ViewModel tests

## Current Implementation Status

**Real Google Sign-In**: The app now uses the modern CredentialManager API with Google Identity Services for real Google Sign-In authentication.

**Key Features**:
- Real Google Sign-In with CredentialManager
- Proper error handling for missing Google accounts
- "Add Google Account" flow when no account is found
- Real user data persistence in Room database
- Firebase Authentication integration

## Google Sign-In Flow

1. **User clicks "Sign in with Google"**
2. **CredentialManager checks for Google accounts**
3. **If account found**: Authenticates with Firebase
4. **If no account**: Shows "Add Google Account" button
5. **User adds account**: Retries authentication
6. **Success**: Saves user data and navigates to home

## Real User Data

The app now uses real Google account data:
- **Name**: From Google account display name
- **Email**: From Google account email
- **Profile Image**: From Google account photo URL
- **ID**: Firebase user UID

## Future Enhancements

- Implement proper error handling for network issues
- Add user profile editing functionality
- Implement offline-first architecture
- Add biometric authentication
- Add user preferences and settings
- Implement proper image caching
- Add analytics and crash reporting