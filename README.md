# 🎙️ IHearYou

**IHearYou** is an Android app that listens to the user’s voice and responds both *visually* and *audibly*.  
When the user says **"Blue"** or **"Red"**, the app displays a corresponding color screen and speaks back —  
“Here is the blue screen” or “Here is the red screen”.

---

## 📱 Features

- 🎤 **Speech Recognition** — Detects when the user says *“Blue”* or *“Red”* using Android’s `SpeechRecognizer`.
- 🗣️ **Text-to-Speech (TTS)** — Responds to the user with a spoken confirmation.
- 🎨 **Dynamic UI Feedback** — Displays a screen of the recognized color.
- ⚙️ **MVVM Architecture** — Logic is cleanly separated using a `ViewModel` for better state management and scalability.

---

## 🧩 Tech Stack

| Component | Description |
|------------|-------------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **Speech Recognition** | Android’s `SpeechRecognizer` API |
| **Text to Speech (TTS)** | Android’s `TextToSpeech` API |
| **State Management** | `StateFlow` / `MutableStateFlow` inside `ViewModel` |

---

## 🧠 How It Works

1. The app listens for voice input using Android’s **SpeechRecognizer**.
2. When the user says:
   - `"Blue"` → app speaks *“Here is the blue screen”* and changes the background to **blue**.
   - `"Red"` → app speaks *“Here is the red screen”* and changes the background to **red**.
3. The recognized text and UI state are handled by a **ViewModel**, ensuring clean separation between UI and logic.

---

## 🔐 Permissions

Add the following permission to your AndroidManifest.xml:

```<uses-permission android:name="android.permission.RECORD_AUDIO" />```

