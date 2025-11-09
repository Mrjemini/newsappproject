# newsapp - Android Kotlin Project

A modern Android application built with Kotlin featuring news browsing, messaging simulation, and profile management.

## Features

### Home Tab
- **News Display**: Browse top headlines from NewsAPI
- **Featured Section**: Horizontal scrolling featured news carousel
- **Search Functionality**: Real-time local search through news articles
- **Pull-to-Refresh**: Swipe down to refresh news content
- **Offline Support**: Full offline mode with Room database caching
- **Pagination Ready**: Architecture supports pagination (can be extended)
- **Connectivity Indicator**: Visual indicator when offline

### Messages Tab
- **Offline Chat Simulation**: Fully local chat interface
- **Text Messages**: Send and receive text messages
- **Image Messages**: Send images from gallery
- **Message Bubbles**: Different colors for sent/received messages
- **Auto-scroll**: Automatically scrolls to newest message
- **Timestamps**: Displays time for each message
- **Simulate Receive**: Button to simulate incoming messages
- **Persistent Storage**: Messages stored in Room database

### Profile Tab
- **Profile Image**: Display and update profile picture
- **Camera Integration**: Capture photos directly
- **Gallery Access**: Select images from device gallery
- **Location Services**: Get and display current GPS location
- **Permission Handling**: Proper runtime permission management for Android 6-15

## Tech Stack

### Core
- **Language**: Kotlin
- **Architecture**: Clean Architecture (Data/Domain/Presentation layers)
- **UI**: XML Layouts with ViewBinding
- **Min SDK**: 23 (Android 6.0)
- **Target SDK**: 35 (Android 15)

### Libraries

#### Networking
- Retrofit 2.11.0
- OkHttp 4.12.0
- Gson Converter 2.11.0

#### Local Storage
- Room 2.6.1
- DataStore Preferences 1.1.1
- Security Crypto 1.1.0-alpha06

#### Dependency Injection
- Hilt 2.51.1
- Hilt Android 2.51.1

#### Asynchronous
- Kotlin Coroutines 1.8.1
- Coroutines Flow

#### UI Components
- Material 3 Components 1.12.0
- Constraint Layout 2.1.4
- SwipeRefreshLayout 1.1.0
- Fragment KTX 1.8.4
- Navigation Component 2.8.2

#### Image Loading
- Glide 4.16.0

#### Background Work
- WorkManager 2.9.1

#### Location
- Play Services Location 21.3.0

#### Testing
- JUnit 4.13.2
- Mockito 5.12.0
- Mockito Kotlin 5.3.1
- Coroutines Test 1.8.1
- Arch Core Testing 2.2.0

## Setup Instructions

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 35

### Installation

1. **Clone/Extract the project**
   ```bash
   # If from Git
   git clone <repository-url>
   cd newsapp

   # If from ZIP
   unzip newsapp.zip
   cd newsapp
   ```

2. **Get NewsAPI Key**
   - Visit https://newsapi.org/
   - Sign up for a free API key
   - Copy your API key

3. **Configure API Key**
   - Open `app/src/main/java/com/example/newsapp/data/remote/NewsApi.kt`
   - Replace `YOUR_API_KEY` with your actual API key:
   ```kotlin
   companion object {
       const val BASE_URL = "https://newsapi.org/"
       const val API_KEY = "your_actual_api_key_here"
   }
   ```

4. **Sync Project**
   - Open project in Android Studio
   - Wait for Gradle sync to complete
   - Resolve any dependency issues if prompted

5. **Run the App**
   - Connect Android device or start emulator
   - Click Run button or press Shift+F10
   - Grant permissions when prompted

## API Endpoints

### Base URL
```
https://newsapi.org/
```

### Endpoints Used

#### Get Top Headlines
```
GET /v2/top-headlines
Parameters:
  - country: String (default: "us")
  - apiKey: String (required)
  - page: Int (default: 1)
  - pageSize: Int (default: 20)

Example:
https://newsapi.org/v2/top-headlines?country=us&apiKey=YOUR_API_KEY
```

## Project Structure

```
app/src/main/java/com/example/newsapp/
├── data/
│   ├── local/
│   │   ├── NewsDatabase.kt
│   │   ├── dao/
│   │   │   ├── NewsDao.kt
│   │   │   └── MessageDao.kt
│   │   └── entities/
│   │       ├── NewsEntity.kt
│   │       └── MessageEntity.kt
│   ├── remote/
│   │   ├── NewsApi.kt
│   │   └── dto/
│   │       └── NewsResponse.kt
│   └── repository/
│       ├── NewsRepository.kt
│       └── MessageRepository.kt
├── domain/
│   └── model/
│       ├── News.kt
│       └── Message.kt
├── presentation/
│   ├── home/
│   │   ├── HomeFragment.kt
│   │   ├── HomeViewModel.kt
│   │   └── adapter/
│   ├── messages/
│   │   ├── MessagesFragment.kt
│   │   ├── MessagesViewModel.kt
│   │   └── adapter/
│   └── profile/
│       ├── ProfileFragment.kt
│       └── ProfileViewModel.kt
├── di/
│   ├── AppModule.kt
│   └── DatabaseModule.kt
├── util/
│   ├── ConnectivityObserver.kt
│   ├── NetworkConnectivityObserver.kt
│   └── DateUtils.kt
├── MainActivity.kt
└── NewsApplication.kt
```

## Architecture Details

### Clean Architecture Layers

1. **Presentation Layer**
   - Fragments (UI)
   - ViewModels (Business Logic)
   - Adapters (RecyclerView)
   - Uses StateFlow for reactive UI updates

2. **Domain Layer**
   - Model classes (Business entities)
   - Use cases (Optional, can be added)

3. **Data Layer**
   - Repository (Single source of truth)
   - Local data source (Room)
   - Remote data source (Retrofit)
   - DTOs and Entities

### Design Patterns
- **Repository Pattern**: Abstracts data sources
- **Observer Pattern**: StateFlow/LiveData for reactive programming
- **Dependency Injection**: Hilt for loose coupling
- **MVVM**: ViewModel + StateFlow + ViewBinding

## Running Tests

### Unit Tests
```bash
# Run all unit tests
./gradlew test

# Run specific test class
./gradlew test --tests HomeViewModelTest

# Run tests with coverage
./gradlew testDebugUnitTestCoverage
```

### Test Results
- Location: `app/build/reports/tests/testDebugUnitTest/index.html`
- Coverage: `app/build/reports/coverage/test/debug/index.html`

### Test Coverage
- ✅ HomeViewModel: Search functionality, refresh logic
- ✅ NewsRepository: API calls, caching, error handling
- ✅ MessageRepository: Send/receive messages

## Permissions

### Required Permissions
- `INTERNET` - Fetch news from API
- `ACCESS_NETWORK_STATE` - Check connectivity
- `CAMERA` - Take profile photos
- `ACCESS_FINE_LOCATION` - Get GPS location
- `ACCESS_COARSE_LOCATION` - Get approximate location

### Storage Permissions (Android Version Specific)
- Android 13+ (SDK 33+): `READ_MEDIA_IMAGES`
- Android 6-12 (SDK 23-32): `READ_EXTERNAL_STORAGE`
- Android 6-9 (SDK 23-28): `WRITE_EXTERNAL_STORAGE`

## Features Breakdown

### Implemented ✅
- Clean Architecture with separation of concerns
- XML UI with Material 3 Design
- Hilt Dependency Injection
- Room Database caching
- Retrofit + Coroutines for networking
- Dark/Light theme support
- Custom fragment transitions
- Internet connectivity observer
- Pull-to-refresh functionality
- Search functionality (local filtering)
- Offline support with cached data
- Message simulation (text + images)
- Camera and gallery integration
- Location services
- Runtime permission handling (SDK 23-35)
- Unit tests for ViewModels and Repositories

### Can Be Extended 🔧
- Pagination for news list
- DataStore for user preferences
- WorkManager for background sync
- News detail screen
- Share news functionality
- Bookmarks/Favorites
- Push notifications

## Troubleshooting

### Common Issues

1. **API Key Error**
   - Ensure you've replaced `YOUR_API_KEY` in `NewsApi.kt`
   - Check API key is valid at newsapi.org

2. **Build Errors**
   - Clean project: Build → Clean Project
   - Rebuild: Build → Rebuild Project
   - Invalidate caches: File → Invalidate Caches and Restart

3. **Permission Errors**
   - Manually grant permissions in device settings
   - Uninstall and reinstall app

4. **Network Errors**
   - Check internet connection
   - Verify API endpoint is accessible
   - Check logcat for detailed errors

## Video Demo

📹 **Demo Video**: [Link to demo video - Upload your screen recording here]

### Recording the Demo
1. Use Android Studio's built-in screen recorder
2. Or use ADB: `adb shell screenrecord /sdcard/demo.mp4`
3. Demonstrate:
   - Home tab with news loading
   - Search functionality
   - Pull-to-refresh
   - Offline mode indicator
   - Messages tab with send/receive
   - Image sending
   - Profile tab with camera/gallery/location
   - Dark/Light theme switching

## Performance Optimizations

- **Image Loading**: Glide with placeholder and error handling
- **Database**: Indexed queries for faster search
- **Coroutines**: Structured concurrency for efficient async operations
- **ViewBinding**: Type-safe view access without findViewById
- **Flow**: Cold streams for reactive data
- **DiffUtil**: Efficient RecyclerView updates

## Security Considerations

- API keys should be in local.properties (not included here for demo)
- Encrypted SharedPreferences ready for sensitive data
- FileProvider for secure image sharing
- Proper permission checks before accessing resources

## Future Enhancements

- [ ] Add pagination for news feed
- [ ] Implement bookmarks with Room
- [ ] Add push notifications
- [ ] User authentication
- [ ] Share functionality
- [ ] News categories/filters
- [ ] Multi-language support
- [ ] Accessibility improvements
- [ ] Widget support
- [ ] In-app browser for news articles
