package com.dev.chequpitest.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dev.chequpitest.constant.StringConstants
import com.dev.chequpitest.domain.model.Order
import com.dev.chequpitest.domain.model.User
import com.dev.chequpitest.presentation.navigation.AppNavigation
import com.dev.chequpitest.presentation.ui.viewmodel.CartViewModel
import com.dev.chequpitest.presentation.ui.viewmodel.OrderHistoryViewModel
import com.dev.chequpitest.ui.theme.CheqUpiTestTheme
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {
    private val cartViewModel: CartViewModel by viewModels()
    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    
    private var currentOrder: Order? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheqUpiTestTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController = navController) { amount, user, order ->
                        currentOrder = order
                        startPayment(amount, user)
                    }
                }
            }
        }
        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID(StringConstants.RAZORPAY_KEY_ID)
        Checkout.sdkCheckIntegration(this)

    }

    override fun onPaymentSuccess(p0: String?) {
        Log.d(StringConstants.LOG_TAG_PAYMENT, "success: $p0")
        Toast.makeText(this,
            StringConstants.PAYMENT_SUCCESS, Toast.LENGTH_LONG).show()
        // Update order status to success
        currentOrder?.let { order ->
            orderHistoryViewModel.updateOrderToSuccess(order, p0)
        }
        cartViewModel.clearCart()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d(StringConstants.LOG_TAG_PAYMENT, "error: $p0 - $p1")
        Toast.makeText(this,
            StringConstants.PAYMENT_FAILED, Toast.LENGTH_LONG).show()
        
        // Update order status to failed
        currentOrder?.let { order ->
            orderHistoryViewModel.updateOrderToFailed(order)
        }
        cartViewModel.clearError()
    }

 private   fun startPayment(amount: Double, user: User?) {
        try {
            val co = Checkout()
            val options = JSONObject()
            options.put("name", StringConstants.PAYMENT_NAME)
            options.put("description", StringConstants.PAYMENT_DESCRIPTION)
            options.put("currency", StringConstants.CURRENCY_INR)
            options.put("amount", (amount * 100).toInt())
            
            // Use user profile data if available, otherwise use defaults
            options.put("prefill.email", user?.email ?: "")
            options.put("prefill.contact", user?.phone ?: "")
            
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 5)
            options.put("retry", retryObj)
            co.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "${StringConstants.ERROR_PAYMENT_INITIALIZATION}: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}