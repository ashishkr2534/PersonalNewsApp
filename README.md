# 📰 NewsFlash - Modern News Reader App (Jetpack Compose + Firebase Auth + Room DB)

**Personal New App** is a personalized news reader app built using **Jetpack Compose** and **Kotlin**. It offers a clean, swipe-based interface like Inshorts, with Google Sign-In, location tracking, image upload, offline support, and notifications – all powered by modern Android architecture.

---

## 🚀 Features

### 🔐 Authentication
- Google Sign-In using **Firebase Auth**
- Fetch and store user profile details: Name, Email, Profile Picture
- Conditional navigation logic:
  - If user is logged in → Show **Home (NewsList) Screen**
  - If not logged in → Show **Login Screen**

### 📱 Home Screen
- Full-screen Inshorts-style swipe navigation (one news per screen)
- Location (Latitude & Longitude) shown at **top of screen**
- Notification permission prompt shown on first visit from Menu Button
- News content fetched from [NewsAPI.org](https://newsapi.org/)
- Offline caching with Room DB
- Pull-to-refresh & swipe gestures (delete/view)

### 🔍 Search Screen
- List/Grid layout for viewing multiple news articles
- selection chips for new source to select from 
- Live search functionality for articles

### 👤 Profile Screen
- View and update user profile details
- Change profile picture from:
  - 📷 Camera
  - 🖼️ Gallery
- All permissions handled with dialogs & fallbacks
- Profile image is stored locally and synced across all screens

### 🔔 Notifications
- Prompt for Notification permission on Home screen
- Notification on app open When news fetched
- Future updates will include breaking news alerts

---
## 🧰 Screenshots
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/e5b77be4-f690-41dd-b1d9-1631e2318014" alt="Glassified UI 1" width="200"/>
  <img src="https://github.com/user-attachments/assets/4850d77c-904f-4cae-a7c5-72360d1e8dfe" alt="Glassified UI 2" width="200"/>
     <img src="https://github.com/user-attachments/assets/688593f0-8dd0-431e-bbb3-20b75823a69d" alt="Glassified UI 1" width="200"/>
  <img src="https://github.com/user-attachments/assets/c6e18201-036c-4440-9001-dd3e21e3327b" alt="Glassified UI 2" width="200"/>
</div>




## 🧠 Data Persistence

- All **user data** and **news articles** are stored locally using **Room Database**
- Ensures smooth performance and offline access
- Profile images saved locally and fetched efficiently

---

## 🌐 Location Access

- User's **latitude and longitude** is displayed on **Home screen (top area)**
- Automatically updated with proper permission handling

---

## 🧰 Tech Stack

| Tech | Purpose |
|------|---------|
| **Kotlin** | Primary language |
| **Jetpack Compose** | Declarative UI |
| **Firebase Auth** | Google Sign-In |
| **Retrofit** | API communication |
| **Room DB** | Local storage for users and news |
| **Paging 3** | Pagination for smooth news loading |
| **WorkManager** | Periodic news background refresh |
| **Coil** | Image loading |
| **Location API** | Fetching current user location |
| **Permissions API** | Handling runtime permissions |

---

## 🛠️ Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/ashishkr2534/PersonalNewsApp.git
cd PersonalNewsApp

## 👨‍💻 Developed by  
Ashish – Android Developer  
[LinkedIn – ashishkr2534](https://www.linkedin.com/in/ashishkr2534)  
Tech Focus: Jetpack Compose, Kotlin, SwiftUI, System Architecture
