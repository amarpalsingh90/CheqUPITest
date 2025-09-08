# Firebase Database Setup

## Database Structure

The Firebase Realtime Database should have the following structure:

```json
{
  "products": {
    "1": {
      "id": 1,
      "title": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
      "price": 109.95,
      "description": "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
      "category": "men's clothing",
      "image": "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_t.png",
      "rating": {
        "rate": 3.9,
        "count": 120
      }
    },
    "2": {
      "id": 2,
      "title": "Mens Casual Premium Slim Fit T-Shirts",
      "price": 22.3,
      "description": "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing.",
      "category": "men's clothing",
      "image": "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_t.png",
      "rating": {
        "rate": 4.1,
        "count": 259
      }
    }
  }
}
```

## Database Rules

For development, use these rules in Firebase Console:

```json
{
  "rules": {
    "products": {
      ".read": true,
      ".write": true
    }
  }
}
```

For production, implement proper authentication and security rules:

```json
{
  "rules": {
    "products": {
      ".read": "auth != null",
      ".write": "auth != null && auth.token.admin == true"
    }
  }
}
```

## Adding Sample Data

You can add sample data manually in Firebase Console or use the `SampleDataSetup` class provided in the project.

### Manual Setup in Firebase Console:

1. Go to Firebase Console → Realtime Database
2. Click on "Start in test mode" or "Create database"
3. Add the sample data structure shown above

### Using SampleDataSetup:

```kotlin
// In your Application class or a test activity
@Inject
lateinit var sampleDataSetup: SampleDataSetup

// Call this method to populate sample data
sampleDataSetup.setupSampleData()
```

## Testing the App

1. Ensure Firebase is properly configured
2. Add sample data to the database
3. Run the app
4. You should see the product list with real-time updates
5. Try adding/editing products in Firebase Console to see real-time updates in the app
