package com.dev.chequpitest.presentation

import android.app.Activity
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
import com.dev.chequpitest.presentation.navigation.AppNavigation
import com.dev.chequpitest.presentation.ui.viewmodel.CartViewModel
import com.dev.chequpitest.ui.theme.CheqUpiTestTheme
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {
    private val viewModel: CartViewModel by viewModels()
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
                    AppNavigation(navController = navController) { amount ->
                        startPayment(amount)
                    }
                }
            }
        }
        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID("rzp_test_RF4p9vo7HvkUVV")
        Checkout.sdkCheckIntegration(this)

    }

    override fun onPaymentSuccess(p0: String?) {
        Log.d("Payment", "success: $p0")
        viewModel.clearCart()

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        viewModel.clearError()
    }

    fun startPayment(amount: Double) {
        try {
            val co = Checkout()
            val options = JSONObject()
            options.put("name", "CheqUpi Test")
            options.put("description", "Rozorpay Payment")
            options.put("currency", "INR");
            options.put("amount", (amount * 100).toInt())
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 5)
            options.put("retry", retryObj)
            co.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}