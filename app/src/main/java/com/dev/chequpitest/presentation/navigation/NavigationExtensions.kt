package com.dev.chequpitest.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

/**
 * Type-safe navigation extensions for NavController
 */

fun NavController.navigateToSplash() {
    navigate(AppRoutes.Splash.route)
}

fun NavController.navigateToLogin() {
    navigate(AppRoutes.Login.route)
}

fun NavController.navigateToHome() {
    navigate(AppRoutes.Home.route)
}

fun NavController.navigateToProfile() {
    navigate(AppRoutes.Profile.route)
}

fun NavController.navigateToCheckout() {
    navigate(AppRoutes.Checkout.route)
}

fun NavController.navigateToOrderSuccess(orderId: String, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(AppRoutes.OrderSuccess.createRoute(orderId), builder)
}

/**
 * Navigation with pop-up behavior
 */
fun NavController.navigateAndPopUpTo(
    destination: AppRoutes,
    popUpTo: AppRoutes,
    inclusive: Boolean = false,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(destination.route) {
        popUpTo(popUpTo.route) {
            this.inclusive = inclusive
        }
        builder()
    }
}

/**
 * Navigation with clear back stack
 */
fun NavController.navigateAndClearBackStack(destination: AppRoutes) {
    navigate(destination.route) {
        popUpTo(0) {
            inclusive = true
        }
    }
}

/**
 * Pop back stack with specific destination
 */
fun NavController.popBackTo(destination: AppRoutes, inclusive: Boolean = false) {
    popBackStack(destination.route, inclusive)
}
