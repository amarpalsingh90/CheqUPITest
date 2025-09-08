package com.dev.chequpitest.data.auth

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.dev.chequpitest.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleSignInHelper @Inject constructor(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun signInWithGoogle(): Result<com.google.firebase.auth.FirebaseUser> {
        return try {
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getCredentialOptions())
                .build()

            val result = credentialManager.getCredential(context, request)
            
            when (result.credential) {
                is CustomCredential -> {
                    if (result.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
                        val googleTokenId = googleIdTokenCredential.idToken
                        val authCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
                        val firebaseUser = firebaseAuth.signInWithCredential(authCredential).await().user
                        
                        Log.d("GoogleSignInHelper", "signInWithGoogle: ${firebaseUser?.displayName}")
                        Log.d("GoogleSignInHelper", "signInWithGoogle: ${firebaseUser?.email}")
                        Log.d("GoogleSignInHelper", "signInWithGoogle: ${firebaseUser?.photoUrl}")
                        
                        if (firebaseUser != null && !firebaseUser.isAnonymous) {
                            Result.success(firebaseUser)
                        } else {
                            Result.failure(Exception("Sign-in failed: User is null or anonymous"))
                        }
                    } else {
                        Result.failure(Exception("Invalid credential type"))
                    }
                }
                else -> {
                    Result.failure(Exception("Unsupported credential type"))
                }
            }
        } catch (e: NoCredentialException) {
            Log.d("GoogleSignInHelper", "No credential available, user needs to add Google account")
            Result.failure(Exception("No Google account found. Please add a Google account to your device."))
        } catch (e: GetCredentialException) {
            Log.e("GoogleSignInHelper", "GetCredentialException: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("GoogleSignInHelper", "Unexpected error: ${e.message}")
            Result.failure(e)
        }
    }

    private fun getCredentialOptions(): CredentialOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .build()
    }

    fun getAddAccountIntent(): Intent {
        return Intent(Settings.ACTION_ADD_ACCOUNT).apply {
            putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        }
    }
}
