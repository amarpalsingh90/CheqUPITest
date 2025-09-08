package com.dev.chequpitest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.chequpitest.presentation.ui.screen.CheckoutScreen
import com.dev.chequpitest.presentation.ui.screen.HomeScreen
import com.dev.chequpitest.presentation.ui.screen.LoginScreen
import com.dev.chequpitest.presentation.ui.screen.ProfileScreen
import com.dev.chequpitest.presentation.ui.screen.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        
        composable("login") {
            LoginScreen(navController = navController)
        }
        
        composable("home") {
            HomeScreen(navController = navController)
        }
        
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("checkout") {
            CheckoutScreen(navController = navController)
        }
    }
}
