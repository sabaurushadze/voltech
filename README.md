# Voltech

A modern Android e-commerce application for PC components and technical hardware built with Jetpack Compose. Browse and shop for your favorite computer parts with a clean, intuitive interface.

## 📚 Table of Contents

- [Installation & Setup](#installation--setup)
- [API Endpoints](#api-endpoints)
- [Architecture](#architecture)
- [Features & Details](#features--details)
- [Tech Stack](#tech-stack)
- [Contributors](#-contributors)

## Installation & Setup

### Prerequisites

Before you begin, ensure you have the following installed:
- Android Studio (latest stable version)
- Node.js and npm
- A physical Android device or emulator

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

- `http://localhost:3000/items`
- `http://localhost:3000/categories`
- `http://localhost:3000/favorites`
- `http://localhost:3000/recently`
- `http://localhost:3000/cart`
- `http://localhost:3000/reviews`
- `http://localhost:3000/sellers`

## Architecture

The project follows a **multi-module architecture** organized into feature modules and shared core modules:

```
:app                         
:build-logic    
  └─ :convention
:core       
  ├─ :presentation                
  ├─ :data                  
  └─ :domain    
:core-ui                         
:feature
  ├─ :auth 
      ├─ :presentation                
      ├─ :data                  
      └─ :domain   
├─ :home
      ├─ :presentation                
      ├─ :data                  
      └─ :domain 
├─ :profile
      ├─ :presentation                
      ├─ :data                  
      └─ :domain 
├─ :search
      ├─ :presentation                
      ├─ :data                  
      └─ :domain 
├─ :selling
      ├─ :presentation                
      ├─ :data                  
      └─ :domain       
```

## Features & Details

### Authentication
- **Register/Login**: Users can create an account with email and password and log in.

### Home Screen
- **Browse Categories**: View items grouped by category.
- **Recently Viewed Items**: Quickly access items the user has interacted with.

### Search
- **Live Search**: Search items with live suggestions.
- **Recently Searched Items**: Quick access to previous searches.
- **Filtering & Sorting**: Filter search results by price, category, or other attributes, and sort them.

### Item Details
- **Item Information**: View full details of each item.
- **Image Zoom**: Zoom in on photos; supports multiple images per item.
- **Favorite from Details**: Add/remove item from watchlist directly.
- **Reviews**: Leave positive, neutral, or negative feedback with comments.
- **Cart**: Add items to the cart and view them.

### User Profile
- **View User Items**: See items listed by any user.
- **View Feedback**: Check all reviews given to the user and their average positive feedback.
- **Sorting & Filtering Reviews**: Easily sort feedback for clarity.
- **Profile Management**: Change profile picture, username, and toggle between light/dark theme.
- **Recently Viewed Items**: Check all recently viewed items and delete single or multiple items.
- **Watchlist**: Check all watchlisted items and delete single or multiple items.
- **Sign Out**: Log out from the account.

### Selling
- **View My Items**: See all items posted by the logged-in user.
- **Add Items**: Add up to 5 items with multiple photos per item.
- **Edit/Delete Items**: Modify or remove your items.

### Real-Time Updates
- **Live Updates**: Changes in items, watchlist, or reviews appear in real-time using JSON Server.

### Extras
- **Unit Testing**: Features covered with JUnit, MockK, Turbine, and Coroutines Test.
- **Smooth Navigation**: Handles complex flows with Navigation Compose 2.
- **Asynchronous & Reactive**: Kotlin Coroutines and Flow for efficient background and reactive data handling.


## Tech Stack

### Architecture & Design
- Modular MVI Architecture
- Clean Architecture

### UI
- Jetpack Compose
- Material Design 3
- Coil

### Dependency Injection
- Dagger Hilt

### Networking
- Retrofit
- Kotlinx Serialization
- JSON Server (mock backend)

### Local Storage
- DataStore

### Asynchronous Programming
- Kotlin Coroutines
- Flow

### Pagination
- Paging 3
- Paging Compose

### Firebase Services
- Authentication
- Storage
- Crashlytics

### Testing
- JUnit
- MockK
- Turbine
- Coroutines Test
- Espresso

### Other Utilities
- Splash Screen
- ExifInterface
- Zoomable Image [Telephoto](https://github.com/saket/telephoto)

## 👥 Contributors

* [Vaniko Geladze](https://github.com/geladzevaniko)
* [Saba Urushadze](https://github.com/sabaurushadze)
