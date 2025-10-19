# 😴 Sleep Cycle Analyzer

![Platform](https://img.shields.io/badge/platform-Android-brightgreen.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-100%25-blue.svg)

A privacy-focused Android application that uses your phone's built-in sensors to track and analyze your sleep patterns. This app provides detailed reports on your sleep stages (Light, Deep, REM) and offers insights to help you improve your sleep quality, all without the need for external wearables.

---

## ✨ Features

* **Automatic Sleep Tracking:** Uses the accelerometer and microphone to detect movement and sound during sleep.
* **Sleep Stage Analysis:** Employs on-device machine learning to classify sleep into distinct stages.
* **Daily Sleep Reports:** Generates a detailed report each morning with your sleep score, duration, and stage breakdown.
* **Data Visualization:** Interactive charts show your sleep timeline and trends over time.
* **Privacy First:** All sensor data is processed directly on your device and is never sent to a server.
* **Permission Handling:** Gracefully requests necessary permissions with clear explanations.

---

## 🛠️ Tech Stack & Architecture

This project is built with a modern, scalable, and maintainable tech stack, following best practices for Android development.

### Tech Stack

* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for a fully declarative UI.
* **Language:** [Kotlin](https://kotlinlang.org/) (100%).
* **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://developer.android.com/kotlin/flow) for managing background tasks and data streams.
* **Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture principles.
* **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for managing dependencies.
* **Database:** [Room](https://developer.android.com/training/data-storage/room) for local persistence of sleep data.
* **Permissions:** [Accompanist Permissions](https://google.github.io/accompanist/permissions/) for a streamlined, Compose-friendly approach.
* **Machine Learning:** TensorFlow Lite for on-device model inference.

### Architecture

The app follows a layered architecture to ensure a clean separation of concerns:

* **Presentation Layer:** Composables and ViewModels responsible for the UI and state management.
* **Domain Layer:** Contains the core business logic in the form of Use Cases. This layer is pure Kotlin, independent of the Android framework.
* **Data Layer:** The single source of truth, implemented using the Repository pattern. It coordinates multiple data sources (sensors, local database) and is responsible for all data operations.

---

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

* Android Studio Iguana | 2023.2.1 or later.
* JDK 17.

### Installation

1.  **Clone the repository**
    ```sh
    git clone [https://github.com/varunkumar2004/SleepBud.git](https://github.com/varunkumar2004/SleepBud.git)
    ```
2.  **Open in Android Studio**
    * Open Android Studio.
    * Select `File > Open` and navigate to the cloned project directory.
3.  **Build the Project**
    * Wait for Gradle to sync the project dependencies.
    * Click the "Run" button to build and install the app on an emulator or a physical device.

---

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for more information.
