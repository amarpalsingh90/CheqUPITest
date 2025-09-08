package com.dev.chequpitest.presentation.navigation

sealed class AppRoutes(val route: String) {
    object Splash : AppRoutes("splash")
    object Login : AppRoutes("login")
    object Home : AppRoutes("home")
    object Profile : AppRoutes("profile")
    object Checkout : AppRoutes("checkout")
    object OrderSuccess : AppRoutes("order_success/{orderId}") {
        fun createRoute(orderId: String) = "order_success/$orderId"
    }
}

// Extension functions for type-safe navigation
fun AppRoutes.getRoute(): String = this.route
