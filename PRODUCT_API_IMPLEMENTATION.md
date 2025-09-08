# Product API Implementation with Retrofit

## Overview

The application now includes a complete product fetching system using Retrofit to consume the DummyJSON API. The implementation follows MVVM Clean Architecture principles and integrates seamlessly with the existing Google Sign-In functionality.

## API Endpoint

- **Base URL**: `https://dummyjson.com/products`
- **Endpoint**: `/products`
- **Method**: GET
- **Response**: JSON with product list and metadata

## Architecture Implementation

### 1. Domain Layer

#### CheqUpiProduct Domain Model
```kotlin
data class CheqUpiProduct(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)
```

#### Product Domain Model
```kotlin
data class Product(
    val availabilityStatus: String,
    val brand: String,
    val category: String,
    val description: String,
    val dimensions: Dimensions,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val meta: Meta,
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val returnPolicy: String,
    val reviews: List<Review>,
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val tags: List<String>,
    val thumbnail: String,
    val title: String,
    val warrantyInformation: String,
    val weight: Int
)
```

#### Repository Interface
```kotlin
interface ProductRepository {
    suspend fun getProducts(): Result<CheqUpiProduct>
}
```

#### Use Case
```kotlin
class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Result<CheqUpiProduct> {
        return productRepository.getProducts()
    }
}
```

### 2. Data Layer

#### Retrofit API Service
```kotlin
interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): CheqUpiProductDto
}
```

#### DTOs (Data Transfer Objects)
- `CheqUpiProductDto`: Main response wrapper
- `ProductDto`: Individual product data
- `DimensionsDto`: Product dimensions
- `MetaDto`: Product metadata
- `ReviewDto`: Product reviews

#### Repository Implementation
```kotlin
@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productApiService: ProductApiService
) : ProductRepository {
    override suspend fun getProducts(): Result<CheqUpiProduct> {
        return try {
            val response = productApiService.getProducts()
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

#### Mappers
- `ProductMapper`: Converts DTOs to domain models
- Extension functions for clean mapping: `toDomain()`

### 3. Presentation Layer

#### UI State
```kotlin
sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Success(val products: CheqUpiProduct) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}
```

#### ViewModel
```kotlin
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()
    
    fun loadProducts() { /* Implementation */ }
    fun refreshProducts() { /* Implementation */ }
}
```

#### UI Components
- `ProductItem`: Individual product card component
- `HomeScreen`: Updated to show products instead of dummy data
- Proper state handling (Loading, Success, Error)

## Dependency Injection

### AppModule Configuration
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson { /* Implementation */ }
    
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit { /* Implementation */ }
    
    @Provides
    @Singleton
    fun provideProductApiService(retrofit: Retrofit): ProductApiService { /* Implementation */ }
    
    @Provides
    @Singleton
    fun provideProductRepository(productApiService: ProductApiService): ProductRepository { /* Implementation */ }
}
```

## UI Features

### HomeScreen Updates
1. **Removed Dummy Data**: Replaced static content with dynamic product data
2. **Loading State**: Shows loading indicator while fetching products
3. **Error Handling**: Displays error messages with retry functionality
4. **Product List**: Scrollable list of products with images, ratings, and details
5. **Refresh Functionality**: Pull-to-refresh and FAB refresh button
6. **Navigation**: Maintains existing navigation drawer functionality

### ProductItem Component
- **Product Image**: AsyncImage with Coil for efficient loading
- **Product Details**: Title, price, description, category
- **Rating Display**: Star icon with rating value
- **Stock Status**: Color-coded stock information
- **Discount Badge**: Shows discount percentage when available
- **Material Design 3**: Modern card-based layout

## State Management

### Loading State
- Circular progress indicator
- "Loading products..." message
- Centered layout

### Success State
- Product list with LazyColumn
- Header card showing product count
- Individual product cards
- Empty state handling

### Error State
- Error message display
- Retry button functionality
- User-friendly error messages

## Network Configuration

### Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### Retrofit Configuration
- **Base URL**: From Constants.kt
- **Gson Converter**: For JSON parsing
- **Error Handling**: Try-catch with Result wrapper

## Testing

### Unit Tests
- `GetProductsUseCaseTest`: Tests use case functionality
- Mock repository for isolated testing
- Proper test data setup

### Test Data
- Complete product model with all fields
- Realistic test values
- Proper Result handling

## Error Handling

### Network Errors
- Connection timeouts
- HTTP error codes
- JSON parsing errors

### User Experience
- Clear error messages
- Retry functionality
- Loading states
- Offline handling (future enhancement)

## Performance Considerations

### Image Loading
- Coil for efficient image loading
- Proper content scaling
- Memory management

### List Performance
- LazyColumn for large lists
- Efficient recomposition
- Proper state management

## Future Enhancements

1. **Caching**: Implement local caching with Room
2. **Pagination**: Add pagination for large product lists
3. **Search**: Add product search functionality
4. **Filtering**: Category and price filtering
5. **Offline Support**: Cache products for offline viewing
6. **Pull-to-Refresh**: Implement pull-to-refresh gesture
7. **Product Details**: Navigate to detailed product view
8. **Favorites**: Add favorite products functionality

## Integration with Existing Features

### Authentication
- Products are shown only to authenticated users
- Maintains existing Google Sign-In flow
- Profile and logout functionality preserved

### Navigation
- Navigation drawer still functional
- Profile screen accessible
- Proper back stack management

### Architecture
- Follows existing MVVM Clean Architecture
- Uses same dependency injection pattern
- Maintains separation of concerns

## API Response Structure

The DummyJSON API returns:
```json
{
  "products": [
    {
      "id": 1,
      "title": "iPhone 9",
      "description": "An apple mobile which is nothing like apple",
      "price": 549,
      "discountPercentage": 12.96,
      "rating": 4.69,
      "stock": 94,
      "brand": "Apple",
      "category": "smartphones",
      "thumbnail": "https://i.dummyjson.com/data/products/1/thumbnail.jpg",
      "images": ["https://i.dummyjson.com/data/products/1/1.jpg"],
      // ... other fields
    }
  ],
  "total": 100,
  "skip": 0,
  "limit": 30
}
```

This implementation provides a complete, production-ready product fetching system that integrates seamlessly with the existing authentication and navigation features.
