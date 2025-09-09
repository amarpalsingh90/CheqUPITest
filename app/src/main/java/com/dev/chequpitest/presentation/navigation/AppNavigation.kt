package com.dev.chequpitest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.chequpitest.domain.model.Order
import com.dev.chequpitest.domain.model.User
import com.dev.chequpitest.presentation.ui.screen.CheckoutScreen
import com.dev.chequpitest.presentation.ui.screen.HomeScreen
import com.dev.chequpitest.presentation.ui.screen.LoginScreen
import com.dev.chequpitest.presentation.ui.screen.OrderHistoryScreen
import com.dev.chequpitest.presentation.ui.screen.ProfileScreen
import com.dev.chequpitest.presentation.ui.screen.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startPayment: (totalAmount: Double, user: User?, order: Order) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Splash.route
    ) {
        composable(AppRoutes.Splash.route) {
            SplashScreen(navController = navController)
        }
        
        composable(AppRoutes.Login.route) {
            LoginScreen(navController = navController)
        }
        
        composable(AppRoutes.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(AppRoutes.Profile.route) {
            ProfileScreen(navController = navController)
        }
        
        composable(AppRoutes.OrderHistory.route) {
            OrderHistoryScreen(navController = navController)
        }
        
        composable(AppRoutes.Checkout.route) {
            CheckoutScreen(navController = navController, startPayment = startPayment)
        }
        
        composable(AppRoutes.OrderSuccess.route) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            // Since OrderSuccessScreen was removed, navigate back to home
            navController.navigateToHome()
        }
    }
}
