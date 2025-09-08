package com.dev.chequpitest.core

object StringConstants {
    
    // App Information
    const val APP_NAME = "CheqUpi Test"
    const val APP_DESCRIPTION = "Your payment solution"
    
    // Navigation Routes
    const val ROUTE_SPLASH = "splash"
    const val ROUTE_LOGIN = "login"
    const val ROUTE_HOME = "home"
    const val ROUTE_PROFILE = "profile"
    const val ROUTE_CHECKOUT = "checkout"
    const val ROUTE_ORDER_SUCCESS = "order_success/{orderId}"
    
    // Payment Related
    const val PAYMENT_NAME = "CheqUpi Test"
    const val PAYMENT_DESCRIPTION = "Rozorpay Payment"
    const val CURRENCY_INR = "INR"
    const val PAYMENT_SUCCESS = "Payment completed successfully!"
    const val PAYMENT_FAILED = "Payment failed"
    const val PAYMENT_PROCESSING = "Processing Payment..."
    const val PAYMENT_INITIALIZATION_FAILED = "Payment initialization failed"
    
    // Order Related
    const val ORDER_PREFIX = "order_"
    const val ORDER_SUCCESS_DESCRIPTION = "Payment successful"
    const val ORDER_DATE_FORMAT = "dd MMM yyyy, HH:mm"
    
    // UI Text
    const val WELCOME_MESSAGE = "Welcome to CheqUpi Test"
    const val START_PAYMENT = "Start Payment"
    const val PAY_NOW = "Pay Now"
    const val BACK = "Back"
    const val MENU = "Menu"
    const val REFRESH = "Refresh"
    const val RETRY = "Retry"
    const val CONTINUE_SHOPPING = "Continue Shopping"
    const val COMPLETE_CHECKOUT = "Complete Checkout"
    const val PROCEED_TO_CHECKOUT = "Proceed to Checkout"
    
    // Profile
    const val PROFILE = "Profile"
    const val LOGOUT = "Logout"
    
    // Cart
    const val CART = "Cart"
    const val GO_TO_CHECKOUT = "Go to Checkout"
    const val ADD_TO_CART = "Add to Cart"
    const val REMOVE_FROM_CART = "Remove from Cart"
    const val INCREASE_QUANTITY = "Increase quantity"
    const val DECREASE_QUANTITY = "Decrease quantity"
    const val REMOVE = "Remove"
    
    // Order History
    const val ORDER_HISTORY = "Order History"
    const val NO_ORDERS_FOUND = "No orders found"
    const val ORDER_HISTORY_MESSAGE = "Your order history will appear here"
    const val LOADING_ORDERS = "Loading orders..."
    const val ORDER_ID = "Order ID"
    const val TRANSACTION_ID = "Transaction ID"
    const val DATE = "Date"
    const val STATUS = "Status"
    const val TOTAL_ITEMS = "Total Items"
    const val TOTAL_AMOUNT = "Total Amount"
    const val ORDER_SUMMARY = "Order Summary"
    const val ORDER_DATE = "Order Date"
    
    // Payment Screen
    const val PAYMENT = "Payment"
    const val ENTER_PAYMENT_AMOUNT = "Enter Payment Amount"
    const val AMOUNT_RUPEES = "Amount (₹)"
    const val PAY_NOW_BUTTON = "Pay Now"
    
    // Checkout Screen
    const val CHECKOUT = "Checkout"
    const val YOUR_CART_IS_EMPTY = "Your cart is empty"
    const val CART_IS_EMPTY_MESSAGE = "Add some items to your cart to proceed with checkout"
    
    // Success/Error Messages
    const val ORDER_PLACED_SUCCESSFULLY = "Order Placed Successfully!"
    const val ORDER_CONFIRMATION = "Order Confirmation"
    const val BACK_TO_HOME = "Back to Home"
    const val ERROR_LOADING_CART = "Error loading cart"
    const val ERROR_LOADING_ORDERS = "Failed to load orders"
    const val ERROR_SAVING_ORDER = "Failed to save order"
    
    // Authentication
    const val SIGN_IN_WITH_GOOGLE = "Sign in with Google"
    const val SIGN_OUT = "Sign Out"
    const val AUTHENTICATED = "Authenticated"
    const val UNAUTHENTICATED = "Unauthenticated"
    
    // Splash Screen
    const val LOADING = "Loading..."
    
    // Product Related
    const val PRODUCTS = "Products"
    const val REFRESH_PRODUCTS = "Refresh Products"
    const val ERROR_LOADING_PRODUCTS = "Error loading products"
    const val RETRY_LOADING_PRODUCTS = "Retry loading products"
    
    // Content Descriptions
    const val CONTENT_DESC_APP_LOGO = "App Logo"
    const val CONTENT_DESC_MENU = "Menu"
    const val CONTENT_DESC_REFRESH = "Refresh"
    const val CONTENT_DESC_BACK = "Back"
    const val CONTENT_DESC_PAYMENT = "Payment"
    const val CONTENT_DESC_PAY_NOW = "Pay Now"
    const val CONTENT_DESC_GO_TO_CHECKOUT = "Go to Checkout"
    const val CONTENT_DESC_ADD_TO_CART = "Add to Cart"
    const val CONTENT_DESC_REMOVE_FROM_CART = "Remove from Cart"
    const val CONTENT_DESC_INCREASE_QUANTITY = "Increase quantity"
    const val CONTENT_DESC_DECREASE_QUANTITY = "Decrease quantity"
    const val CONTENT_DESC_REMOVE = "Remove"
    const val CONTENT_DESC_SUCCESS = "Success"
    const val CONTENT_DESC_NO_ORDERS = "No Orders"
    
    // Razorpay Configuration
    const val RAZORPAY_KEY_ID = "rzp_test_RF4p9vo7HvkUVV"
    const val RAZORPAY_EMAIL = "test@example.com"
    const val RAZORPAY_CONTACT = "9999999999"
    const val RAZORPAY_METHOD = "card"
    
    // Database
    const val DATABASE_NAME = "app_database"
    
    // API
    const val API_URL = "https://dummyjson.com/products"
    
    // Error Messages
    const val ERROR_PAYMENT_INITIALIZATION = "Error in payment"
    const val ERROR_PAYMENT_FAILED = "Payment failed with code"
    const val ERROR_GENERIC = "An error occurred"
    const val ERROR_NETWORK = "Network error"
    const val ERROR_UNKNOWN = "Unknown error"
    
    // Validation
    const val VALIDATION_AMOUNT_REQUIRED = "Amount is required"
    const val VALIDATION_AMOUNT_POSITIVE = "Amount must be positive"
    const val VALIDATION_CART_EMPTY = "Cart is empty"
    
    // Formatting
    const val CURRENCY_SYMBOL = "₹"
    const val CURRENCY_FORMAT = "%.2f"
    
    // Log Tags
    const val LOG_TAG_PAYMENT = "Payment"
    const val LOG_TAG_ORDER = "Order"
    const val LOG_TAG_CART = "Cart"
    const val LOG_TAG_AUTH = "Auth"
}
