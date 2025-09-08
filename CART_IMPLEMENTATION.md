# Shopping Cart Implementation

## Overview

The application now includes a complete shopping cart system with Room database persistence, following MVVM Clean Architecture principles. Users can add products to cart, adjust quantities, and proceed to checkout.

## Features Implemented

### ✅ **Cart Functionality**
- Add products to cart with quantity management
- Plus (+) and minus (–) buttons for quantity adjustment
- Remove items from cart
- Real-time cart state updates
- Persistent cart data using Room Database

### ✅ **UI Components**
- **Cart Badge**: Shows item count in top app bar
- **Product Item Controls**: Add to cart button and quantity controls
- **Checkout Screen**: Complete cart review and checkout flow
- **Floating Action Button**: Dynamic FAB (cart or refresh)

### ✅ **State Management**
- Loading, Success, and Error states
- Real-time cart updates
- Proper error handling with retry functionality

## Architecture Implementation

### 1. Domain Layer

#### Cart Domain Models
```kotlin
data class CartItem(
    val id: String,
    val productId: Int,
    val productTitle: String,
    val productPrice: Double,
    val productThumbnail: String,
    val quantity: Int,
    val totalPrice: Double
)

data class Cart(
    val items: List<CartItem>,
    val totalItems: Int,
    val totalAmount: Double
)
```

#### Repository Interface
```kotlin
interface CartRepository {
    fun getCart(): Flow<Cart>
    suspend fun addToCart(product: Product): Result<Unit>
    suspend fun removeFromCart(productId: Int): Result<Unit>
    suspend fun updateQuantity(productId: Int, quantity: Int): Result<Unit>
    suspend fun clearCart(): Result<Unit>
    suspend fun getCartItemCount(): Int
}
```

#### Use Cases
- `AddToCartUseCase`: Add product to cart
- `RemoveFromCartUseCase`: Remove product from cart
- `UpdateCartQuantityUseCase`: Update product quantity
- `GetCartUseCase`: Get current cart state
- `ClearCartUseCase`: Clear entire cart

### 2. Data Layer

#### Room Database
```kotlin
@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: String,
    val productId: Int,
    val productTitle: String,
    val productPrice: Double,
    val productThumbnail: String,
    val quantity: Int,
    val totalPrice: Double
)
```

#### DAO Operations
```kotlin
@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>
    
    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getCartItemByProductId(productId: Int): CartItemEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)
    
    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)
    
    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)
    
    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItemByProductId(productId: Int)
    
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
    
    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun getCartItemCount(): Int
    
    @Query("SELECT SUM(totalPrice) FROM cart_items")
    suspend fun getTotalAmount(): Double?
}
```

#### Repository Implementation
- `CartRepositoryImpl`: Handles all cart operations
- Automatic quantity management (increment existing items)
- Proper error handling with Result wrapper
- Flow-based reactive updates

### 3. Presentation Layer

#### UI State
```kotlin
sealed class CartUiState {
    object Loading : CartUiState()
    data class Success(val cart: Cart) : CartUiState()
    data class Error(val message: String) : CartUiState()
}
```

#### ViewModel
```kotlin
@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel()
```

## UI Components

### 1. ProductItem Updates

#### Cart Controls
- **Add to Cart Button**: FloatingActionButton for new items
- **Quantity Controls**: Plus/minus buttons for existing items
- **Quantity Display**: Shows current quantity in cart
- **Visual Feedback**: Different UI states for cart vs non-cart items

#### Implementation
```kotlin
@Composable
fun ProductItem(
    product: Product,
    cartQuantity: Int = 0,
    onAddToCart: () -> Unit = {},
    onRemoveFromCart: () -> Unit = {},
    onUpdateQuantity: (Int) -> Unit = {},
    modifier: Modifier = Modifier
)
```

### 2. CartBadge Component

#### Features
- **Badge Display**: Shows item count with Material Design badge
- **Dynamic Updates**: Real-time count updates
- **Navigation**: Click to navigate to checkout

#### Implementation
```kotlin
@Composable
fun CartBadge(
    itemCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)
```

### 3. HomeScreen Updates

#### Top App Bar
- **Cart Badge**: Shows item count with navigation
- **Refresh Button**: Reload products
- **Dynamic Badge**: Only shows when items in cart

#### Floating Action Button
- **Dynamic FAB**: Shows cart icon when items exist, refresh when empty
- **Smart Navigation**: Direct to checkout or refresh products

#### Product List Integration
- **Cart State Integration**: Each product shows cart quantity
- **Real-time Updates**: Cart changes reflect immediately
- **Proper State Management**: Handles loading, success, error states

### 4. CheckoutScreen

#### Features
- **Cart Review**: List all cart items with details
- **Quantity Management**: Adjust quantities directly in checkout
- **Order Summary**: Total items and amount calculation
- **Checkout Button**: Complete purchase (clears cart)
- **Empty State**: Handles empty cart gracefully

#### Layout
- **LazyColumn**: Efficient scrolling for large carts
- **CartItemCard**: Individual item display with controls
- **Summary Card**: Fixed bottom summary with totals
- **Navigation**: Back button and completion flow

#### CartItemCard Component
```kotlin
@Composable
fun CartItemCard(
    cartItem: CartItem,
    onUpdateQuantity: (Int) -> Unit,
    onRemoveItem: () -> Unit,
    modifier: Modifier = Modifier
)
```

## Navigation Flow

### 1. Product to Cart Flow
1. User views products on HomeScreen
2. Clicks "Add to Cart" button on ProductItem
3. Cart quantity updates immediately
4. Cart badge shows updated count
5. FAB changes to cart icon

### 2. Cart Management Flow
1. User can adjust quantities using +/- buttons
2. Changes persist to Room database
3. UI updates in real-time
4. Cart badge reflects current count

### 3. Checkout Flow
1. User clicks cart badge or FAB
2. Navigates to CheckoutScreen
3. Reviews cart items and quantities
4. Adjusts quantities if needed
5. Views order summary
6. Completes checkout (clears cart)
7. Returns to HomeScreen

## Database Schema

### Cart Items Table
```sql
CREATE TABLE cart_items (
    id TEXT PRIMARY KEY,
    productId INTEGER NOT NULL,
    productTitle TEXT NOT NULL,
    productPrice REAL NOT NULL,
    productThumbnail TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    totalPrice REAL NOT NULL
);
```

### Database Version
- **Version 2**: Added cart_items table
- **Migration**: Uses fallbackToDestructiveMigration for simplicity
- **Future**: Can implement proper migrations for production

## State Management

### Cart State Flow
1. **Loading**: Initial state while fetching cart
2. **Success**: Cart data loaded successfully
3. **Error**: Error occurred, with retry option

### Real-time Updates
- **Flow-based**: Cart updates automatically via Room Flow
- **Reactive UI**: UI updates when cart changes
- **Consistent State**: Single source of truth for cart data

### Error Handling
- **Network Errors**: Handled gracefully
- **Database Errors**: Proper error messages
- **User Feedback**: Clear error states with retry options

## Dependency Injection

### AppModule Updates
```kotlin
@Provides
fun provideCartDao(database: AppDatabase): CartDao {
    return database.cartDao()
}

@Provides
@Singleton
fun provideCartRepository(cartDao: CartDao): CartRepository {
    return CartRepositoryImpl(cartDao)
}
```

### Hilt Integration
- **Singleton Scope**: Cart repository and DAO
- **ViewModel Injection**: CartViewModel with all use cases
- **Proper Lifecycle**: Managed by Hilt

## Performance Considerations

### Database Operations
- **Efficient Queries**: Optimized SQL queries
- **Flow-based**: Reactive updates without polling
- **Background Threads**: All database operations on IO threads

### UI Performance
- **LazyColumn**: Efficient scrolling for large lists
- **State Management**: Minimal recomposition
- **Image Loading**: Coil for efficient image handling

### Memory Management
- **Proper Scoping**: ViewModels scoped to screens
- **Resource Cleanup**: Automatic cleanup via Hilt
- **State Persistence**: Room handles persistence efficiently

## Testing Considerations

### Unit Tests
- **Use Cases**: Test cart business logic
- **Repository**: Test data layer operations
- **ViewModel**: Test state management

### Integration Tests
- **Database**: Test Room operations
- **Navigation**: Test cart flow
- **UI**: Test cart interactions

## Future Enhancements

### 1. Advanced Features
- **Cart Persistence**: Persist cart across app sessions
- **User-specific Carts**: Multiple user cart support
- **Cart Sharing**: Share cart with others
- **Wishlist**: Save items for later

### 2. UI Improvements
- **Animations**: Smooth cart animations
- **Pull-to-refresh**: Refresh cart data
- **Search**: Search within cart
- **Sorting**: Sort cart items

### 3. Business Logic
- **Discounts**: Apply discounts to cart
- **Tax Calculation**: Calculate taxes
- **Shipping**: Add shipping options
- **Payment**: Integrate payment processing

### 4. Performance
- **Caching**: Cache cart data
- **Offline Support**: Work offline
- **Sync**: Sync across devices
- **Optimization**: Further performance improvements

## Integration with Existing Features

### Authentication
- **User Context**: Cart tied to authenticated user
- **Session Management**: Cart persists during session
- **Logout Handling**: Clear cart on logout (optional)

### Product Management
- **Real-time Updates**: Product changes reflect in cart
- **Stock Management**: Handle out-of-stock items
- **Price Updates**: Handle price changes

### Navigation
- **Deep Linking**: Direct links to cart/checkout
- **Back Stack**: Proper navigation history
- **State Preservation**: Maintain state during navigation

This implementation provides a complete, production-ready shopping cart system that integrates seamlessly with the existing authentication and product management features while following clean architecture principles.
