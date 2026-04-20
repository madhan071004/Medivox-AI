# 🩺 MedAI Android App

Native Android (Kotlin) version of the MedAI Public Health Chatbot.
Converted from React/Web → Native Android using MVVM + Hilt + Retrofit.

## 📁 Project Structure

```
MedAI-Android/
├── app/
│   ├── build.gradle                          # Dependencies
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/medai/app/
│       │   ├── MedAiApp.kt                   # Hilt Application
│       │   ├── data/
│       │   │   ├── api/GroqApiService.kt     # Retrofit API interface
│       │   │   ├── model/Models.kt           # Data classes
│       │   │   └── repository/ChatRepository.kt  # API logic
│       │   ├── di/AppModule.kt               # Hilt DI module
│       │   ├── ui/
│       │   │   ├── splash/SplashActivity.kt
│       │   │   ├── login/
│       │   │   │   ├── LoginActivity.kt
│       │   │   │   └── LoginViewModel.kt
│       │   │   └── chat/
│       │   │       ├── ChatActivity.kt       # Main screen
│       │   │       ├── ChatViewModel.kt      # All state
│       │   │       ├── ChatAdapter.kt        # Messages RecyclerView
│       │   │       └── ConversationAdapter.kt
│       │   └── utils/
│       │       ├── Constants.kt
│       │       └── PrefsManager.kt           # DataStore
│       └── res/
│           ├── layout/                       # All XML layouts
│           ├── drawable/                     # Shapes, icons
│           ├── values/                       # Colors, strings, themes
│           └── font/                         # Sora font files
├── build.gradle
└── settings.gradle
```

## 🚀 Setup in Android Studio

### Step 1 — Open project
1. Open **Android Studio**
2. File → Open → Select `MedAI-Android` folder
3. Wait for Gradle sync

### Step 2 — Add Sora fonts (required)
1. Go to [fonts.google.com/specimen/Sora](https://fonts.google.com/specimen/Sora)
2. Download → Extract zip
3. Copy `Sora-Regular.ttf` → `app/src/main/res/font/sora_regular.ttf`
4. Copy `Sora-Bold.ttf`    → `app/src/main/res/font/sora_bold.ttf`

### Step 3 — Get Groq API Key (free)
1. Go to [console.groq.com](https://console.groq.com)
2. Sign up → API Keys → Create Key
3. Copy key starting with `gsk_...`
4. Enter in app's login screen

### Step 4 — Run
- Connect Android device (API 26+) or start emulator
- Press ▶️ Run in Android Studio

## ✨ Features

| Feature | Implementation |
|---------|---------------|
| Login / Sign Up | LoginActivity + LoginViewModel + DataStore |
| Chat UI | RecyclerView + ChatAdapter + Markwon |
| Groq AI | Retrofit streaming + ChatRepository |
| Voice Input | Android SpeechRecognizer (en-IN / ta-IN) |
| Image Attach | ActivityResultContracts + Glide |
| Dark/Light Mode | AppCompatDelegate.setDefaultNightMode |
| Language Toggle | English ↔ Tamil — saved to DataStore |
| Conversation History | DrawerLayout sidebar |
| API Key Management | DataStore + AlertDialog |
| Markdown Rendering | Markwon library |

## 🏗️ Architecture

```
UI Layer
  LoginActivity / ChatActivity
        ↓
ViewModel Layer
  LoginViewModel / ChatViewModel (StateFlow)
        ↓
Repository Layer
  ChatRepository (Groq API calls)
        ↓
Data Layer
  GroqApiService (Retrofit) + PrefsManager (DataStore)
```

## 📦 Key Dependencies

- **Hilt** — Dependency injection
- **Retrofit + OkHttp** — Groq API calls
- **Markwon** — Markdown rendering in chat
- **Glide** — Image loading
- **DataStore** — Secure key/preference storage
- **Material Components** — UI components
- **Navigation Component** — Screen routing

## ⚕️ Medical Disclaimer
MedAI provides public health awareness only — not a substitute for professional medical advice.
