package com.dev.chequpitest.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dev.chequpitest.R
import com.dev.chequpitest.constant.StringConstants
import com.dev.chequpitest.presentation.navigation.navigateAndPopUpTo
import com.dev.chequpitest.presentation.navigation.AppRoutes
import com.dev.chequpitest.presentation.ui.state.AuthUiState
import com.dev.chequpitest.presentation.ui.viewmodel.AuthViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Loading -> {
                // Keep showing loading
            }
            is AuthUiState.Authenticated -> {
                navController.navigateAndPopUpTo(
                    destination = AppRoutes.Home,
                    popUpTo = AppRoutes.Splash,
                    inclusive = true
                )
            }
            is AuthUiState.Unauthenticated -> {
                navController.navigateAndPopUpTo(
                    destination = AppRoutes.Login,
                    popUpTo = AppRoutes.Splash,
                    inclusive = true
                )
            }
            is AuthUiState.Error -> {
                navController.navigateAndPopUpTo(
                    destination = AppRoutes.Login,
                    popUpTo = AppRoutes.Splash,
                    inclusive = true
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo or Icon
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = StringConstants.CONTENT_DESC_APP_LOGO,
                modifier = Modifier.size(120.dp)
            )
            
            Text(
                text = StringConstants.APP_NAME,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
