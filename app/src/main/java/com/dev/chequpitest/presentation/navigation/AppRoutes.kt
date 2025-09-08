package com.dev.chequpitest.presentation.navigation

import com.dev.chequpitest.constant.StringConstants

sealed class AppRoutes(val route: String) {
    object Splash : AppRoutes(StringConstants.ROUTE_SPLASH)
    object Login : AppRoutes(StringConstants.ROUTE_LOGIN)
    object Home : AppRoutes(StringConstants.ROUTE_HOME)
    object Profile : AppRoutes(StringConstants.ROUTE_PROFILE)
    object Checkout : AppRoutes(StringConstants.ROUTE_CHECKOUT)
    object OrderSuccess : AppRoutes(StringConstants.ROUTE_ORDER_SUCCESS) {
        fun createRoute(orderId: String) = "order_success/$orderId"
    }
}

// Extension functions for type-safe navigation
fun AppRoutes.getRoute(): String = this.route
