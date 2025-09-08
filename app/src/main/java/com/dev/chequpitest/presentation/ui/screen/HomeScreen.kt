package com.dev.chequpitest.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dev.chequpitest.presentation.ui.components.CartBadge
import com.dev.chequpitest.presentation.ui.components.NavigationDrawer
import com.dev.chequpitest.presentation.ui.components.ProductItem
import com.dev.chequpitest.presentation.ui.state.CartUiState
import com.dev.chequpitest.presentation.ui.state.ProductUiState
import com.dev.chequpitest.presentation.ui.viewmodel.AuthViewModel
import com.dev.chequpitest.presentation.ui.viewmodel.CartViewModel
import com.dev.chequpitest.presentation.ui.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    productsViewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    var showDrawer by remember { mutableStateOf(false) }
    val productUiState by productsViewModel.uiState.collectAsState()
    val cartUiState by cartViewModel.uiState.collectAsState()

    val cartItemCount = when (cartUiState) {
        is CartUiState.Success -> {
            (cartUiState as CartUiState.Success).cart.totalItems
        }

        else -> 0
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                navigationIcon = {
                    IconButton(onClick = { showDrawer = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    CartBadge(
                        itemCount = cartItemCount,
                        onClick = { navController.navigate("checkout") }
                    )
                    IconButton(onClick = { productsViewModel.refreshProducts() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            if (cartItemCount > 0) {
                FloatingActionButton(
                    onClick = { navController.navigate("checkout") }
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Go to Checkout")
                }
            } else {
                FloatingActionButton(
                    onClick = { productsViewModel.refreshProducts() }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh Products")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (productUiState) {
                is ProductUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(40.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading products...",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                is ProductUiState.Success -> {
                    val uistate = productUiState as ProductUiState.Success
                    if (uistate.products.products.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No products found",
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = { productsViewModel.refreshProducts() }) {
                                    Text("Refresh")
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            items(uistate.products.products) { product ->
                                val cartItem = when (cartUiState) {
                                    is com.dev.chequpitest.presentation.ui.state.CartUiState.Success ->
                                        (cartUiState as CartUiState.Success).cart.items.find { it.productId == product.id }

                                    else -> null
                                }

                                ProductItem(
                                    product = product,
                                    cartQuantity = cartItem?.quantity ?: 0,
                                    onAddToCart = { cartViewModel.addToCart(product) },
                                    onRemoveFromCart = { cartViewModel.removeFromCart(product.id) },
                                    onUpdateQuantity = { quantity ->
                                        cartViewModel.updateQuantity(product.id, quantity)
                                    }
                                )
                            }
                        }
                    }
                }

                is ProductUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error: ${(productUiState as ProductUiState.Error).message}",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { productsViewModel.refreshProducts() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }

    // Navigation Drawer
    if (showDrawer) {
        NavigationDrawer(
            onDismiss = { showDrawer = false },
            onProfileClick = {
                showDrawer = false
                navController.navigate("profile")
            },
            onLogoutClick = {
                showDrawer = false
                authViewModel.signOut()
            }
        )
    }
}
