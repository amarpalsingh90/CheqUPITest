# Google Sign-In Setup Guide

## Prerequisites

1. Firebase project created
2. Android app added to Firebase project
3. `google-services.json` file downloaded and placed in `app/` directory

## Step-by-Step Setup

### 1. Firebase Console Configuration

1. **Enable Authentication**:
   - Go to Firebase Console → Authentication
   - Click "Get started"
   - Go to "Sign-in method" tab

2. **Enable Google Provider**:
   - Click on "Google" provider
   - Toggle "Enable"
   - Add project support email
   - Save

3. **Get Web Client ID**:
   - In Google provider settings, copy the "Web client ID"
   - This will be used in your Android app

### 2. Android Project Configuration

1. **Update google-services.json**:
   - Ensure the file is in `app/` directory
   - Verify package name matches your app

2. **Add SHA-1 Fingerprint**:
   ```bash
   # For debug build
   keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
   
   # For release build
   keytool -list -v -keystore your-release-key.keystore -alias your-key-alias
   ```
   - Copy SHA-1 fingerprint
   - Add to Firebase Console → Project Settings → Your Android App

3. **Update Code**:
   - Replace `YOUR_WEB_CLIENT_ID` in `GoogleSignInHelper.kt` with your actual Web Client ID
   - Replace `YOUR_WEB_CLIENT_ID` in `AuthRepositoryImpl.kt` with your actual Web Client ID

### 3. Dependencies

The following dependencies are already added to `build.gradle.kts`:

```kotlin
// Firebase
implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
implementation("com.google.firebase:firebase-analytics")
implementation("com.google.firebase:firebase-auth")

// Google Sign-In
implementation("androidx.credentials:credentials:1.2.2")
implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")
```

### 4. Testing the Implementation

1. **Build and Run**:
   - Sync project with Gradle
   - Build and run the app

2. **Test Flow**:
   - App should start with splash screen
   - Navigate to login screen if not authenticated
   - Click "Sign in with Google" button
   - Complete Google Sign-In flow
   - Should navigate to home screen
   - Check profile screen for user data
   - Test logout functionality

### 5. Troubleshooting

**Common Issues**:

1. **"Sign in failed" error**:
   - Check SHA-1 fingerprint is added to Firebase
   - Verify Web Client ID is correct
   - Ensure google-services.json is up to date

2. **"No Google account found"**:
   - Make sure Google Play Services is installed
   - Check if Google account is added to device

3. **Build errors**:
   - Clean and rebuild project
   - Check all dependencies are properly added
   - Verify Hilt setup is correct

### 6. Production Considerations

1. **Security**:
   - Use proper keystore for release builds
   - Add release SHA-1 fingerprint to Firebase
   - Implement proper error handling

2. **User Experience**:
   - Add loading indicators
   - Implement proper error messages
   - Add retry mechanisms

3. **Testing**:
   - Test on different devices
   - Test with different Google accounts
   - Test offline scenarios

## Current Implementation Status

**Note**: The current implementation uses mock data for demonstration purposes. To implement real Google Sign-In:

1. Update `AuthRepositoryImpl.signInWithGoogle()` to use actual Google Sign-In flow
2. Handle activity results properly
3. Implement proper error handling for network issues
4. Add proper user data validation

## Mock Data

Currently, the app uses mock user data:
- Name: "John Doe"
- Email: "john.doe@example.com"
- Profile Image: Placeholder image
- ID: "mock_user_123"

This allows you to test the complete app flow without setting up real Google Sign-In initially.
