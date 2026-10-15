# Simple Wallet

An Android wallet demo with mock sign-in, three currency accounts, and account statements filtered to the most recent 30 days. The app is intentionally small and uses Android's native UI and platform APIs.

## Requirements

- Android Studio with support for Android Gradle Plugin 9.4.1
- JDK 17 or newer
- Android SDK Platform 37 and Android build tools installed through SDK Manager
- Android device or emulator running API 24 or newer

## How to run

1. Clone this repository and open its directory:

   ```sh
   git clone <repository-url>
   cd TNWDEMOAPP
   ```

2. Ensure Android SDK Platform 37 is installed and that `local.properties` points to your SDK. Android Studio can create this local-only file automatically when the project is opened.
3. Build and test from the project root:

   ```sh
   ./gradlew testDebugUnitTest lintDebug assembleDebug
   ```

4. In Android Studio, select the `app` run configuration and launch it on a device or emulator. Alternatively, install `app/build/outputs/apk/debug/app-debug.apk`.

## Demo credentials

- Email: `demo@example.com`
- Password: `password123`

These values are only for the local mock authentication repository; they are not production credentials.

## Architecture

The app separates presentation, domain, and data responsibilities:

```text
Activity -> AppContainer -> repositories/data sources and ViewModel factories
Compose UI -> ViewModels -> WalletRepository -> WalletDataSource -> mock or future remote source
                          -> Domain validation and transaction filtering
```

- **Presentation:** Compose screens render loading, error, empty, and success states. ViewModels handle interaction and screen state.
- **Domain:** wallet/authentication models, repository contracts, login input validation, and last-30-days transaction filtering.
- **Data:** `WalletRepositoryImpl` implements the domain repository and depends on the `WalletDataSource` contract. `MockWalletDataSource` currently provides in-memory data; a future Retrofit-backed implementation can implement the same contract without changes to the repository or screens.
- **Dependency wiring:** `AppContainer` lazily creates the data implementations, preferences repository, and ViewModel factories in one place. It uses native AndroidX factories rather than adding a dependency injection framework.
- **Authentication state:** `LoginViewModel` owns updating and clearing the persisted sign-in flag. The UI observes its state for initial navigation and delegates logout to it rather than accessing preferences directly.

The authenticated flow shows all accounts, opens a selected account's statement, and allows returning to accounts or logging out. Transactions are displayed newest first.

## Dependencies

- Jetpack Compose and Material 3 for the native Android UI.
- AndroidX Activity and Lifecycle ViewModel for the Activity/Compose integration and lifecycle-aware presentation state.
- JUnit 4 for unit tests.

No networking, persistence, or authentication SDK is used.

## Security

Authentication is intentionally mocked. The publicly documented demo password is checked in memory only; it is not stored, logged, or sent over a network. Do not use this mock repository for a production app.

Android Gradle Lint (`./gradlew lintDebug`) completed with no errors and 12 warnings. They are existing template/tooling warnings (one redundant activity label, available dependency/tooling updates, and unused starter color resources); Lint reported no security-category findings. This check is useful for common Android issues but is not a substitute for a production security review.

## Tests

Run the focused local unit tests with:

```sh
./gradlew testDebugUnitTest
```

Tests cover valid/invalid demo credentials, empty and malformed login input, and 30-day transaction filtering and ordering.

## Trade-offs

The wallet data and authentication are in-memory mocks, so changes are not persisted and authentication is not real. Production use would require a secure backend, token/session lifecycle handling, resilient data loading, and currency/date localization review. Paging and additional account/transaction types are not implemented.
