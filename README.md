# Voltech

A modern Android e-commerce application for PC components and technical hardware built with Jetpack Compose. Browse and shop for your desired Computer parts with a clean, intuitive interface.

## Prerequisites

Before you begin, ensure you have the following installed:
- Android Studio (latest stable version)
- Node.js and npm
- A physical Android device or emulator

## Installation & Setup

### 1. JSON Server Setup

Install JSON Server globally with the specific version required:
```bash
npm install -g json-server@0.17.4
```

### 2. Network Configuration

#### For Emulator Testing

If you're using an Android emulator, the default configuration should work out of the box.

#### For Physical Device Testing

To test on a physical device, you'll need to configure network access:

**Step 1: Enable Device Connectivity**
- Enable **Mobile Hotspot** or **USB Tethering** on your development machine
- Connect your physical device to the same network

**Step 2: Get Your IP Address**
- Open Command Prompt (Windows) or Terminal (Mac/Linux)
- Type `ipconfig` (Windows) or `ifconfig` (Mac/Linux)
- Copy your **IPv4 Address** (e.g., `192.168.x.x` or `10.x.x.x`)

**Step 3: Update Network Security Config**

Navigate to `app/src/main/res/xml/network_security_config.xml` and update the domain:
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">10.0.2.2</domain>
        <domain includeSubdomains="true">YOUR_IPV4_ADDRESS</domain>
    </domain-config>
</network-security-config>
```

**Note:** 
- Use `10.0.2.2` for **emulator only**
- Use your actual IPv4 address for **physical device**

**Step 4: Update Base URL**

Navigate to `core/data/build.gradle.kts` and change `10.0.2.2` to your IPv4 address in both debug and release configurations if you want to use your physical device:
```kotlin
debug {
    buildConfigField(
        "String",
        "BASE_URL",
        "\"http://10.0.2.2:3000/\""
    )
}
release {
    isMinifyEnabled = false
    proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
    buildConfigField(
        "String",
        "BASE_URL",
        "\"http://10.0.2.2:3000/\""
    )
}
```

### 3. Firebase Configuration

Add your own `google-services.json` file:
- Download the file from your Firebase Console
- Place it in the `app/` directory

### 4. Running the JSON Server

Open the **Android Studio Terminal** and navigate to the server directory:
```bash
cd server
npm start
```

The server will start on port 3000. You can verify it's running by opening the following URL in your browser:
```
http://localhost:3000/items
```

### 5. Build & Run the App

Once the JSON Server is running:
1. Sync your Gradle files in Android Studio
2. Build the project
3. Run the app on your emulator or physical device

## API Endpoints

The JSON Server provides the following endpoints:

- `http://localhost:3000/items` - Access your data locally
- `http://YOUR_IP:3000/items` - Access from physical device (when configured)

## Tech Stack

### Architecture & Design Pattern
- **MVVM Architecture** - Model-View-ViewModel pattern
- **Clean Architecture** - Separation of concerns with multi-module structure
- **Repository Pattern** - Data abstraction layer

### UI
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern declarative UI toolkit
- **[Material Design 3](https://m3.material.io/)** - Latest Material Design components
- **[Material Icons Extended](https://developer.android.com/jetpack/androidx/releases/compose-material)** - Comprehensive icon set
- **[Coil](https://coil-kt.github.io/coil/)** - Image loading library
- **[Google Fonts](https://fonts.google.com/)** - Custom typography

### Dependency Injection
- **[Dagger Hilt](https://dagger.dev/hilt/)** - Dependency injection framework

### Networking
- **[Retrofit](https://square.github.io/retrofit/)** - Type-safe HTTP client
- **[OkHttp](https://square.github.io/okhttp/)** - HTTP client and interceptor
- **[Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html)** - JSON serialization/deserialization
- **JSON Server v0.17.4** - Mock REST API backend

### Navigation
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)** - Jetpack Navigation for Compose
- **[Type-Safe Navigation](https://developer.android.com/guide/navigation/design/type-safety)** - Kotlinx Serialization for navigation arguments

### Local Storage
- **[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)** - Modern data storage solution for preferences

### Asynchronous Programming
- **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Asynchronous programming
- **[Flow](https://kotlinlang.org/docs/flow.html)** - Reactive streams

### Pagination
- **[Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)** - Efficient data pagination
- **[Paging Compose](https://developer.android.com/jetpack/androidx/releases/paging#compose-integration)** - Paging integration with Compose

### Firebase Services
- **[Firebase Authentication](https://firebase.google.com/docs/auth)** - User authentication
- **[Firebase Crashlytics](https://firebase.google.com/docs/crashlytics)** - Crash reporting
