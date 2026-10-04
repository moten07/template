| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

Это проект Kotlin Multiplatform с поддержкой платформ Android, iOS, Web, Desktop (JVM) и Server.

* [/app/iosApp](./app/iosApp/iosApp) содержит iOS-приложение. Даже если вы используете общий UI на базе Compose
  Multiplatform, вам всё равно нужна эта точка входа для iOS-приложения. Здесь же следует добавлять код на SwiftUI.

* [/app/shared](./app/shared/src) предназначен для кода, который используется совместно во всех приложениях
  Compose Multiplatform. Он содержит несколько подпапок:
    - [commonMain](./app/shared/src/commonMain/kotlin) — код, общий для всех платформ.
    - Остальные папки — для Kotlin-кода, который компилируется только для платформы, указанной в названии папки.
      Например, если вы хотите использовать Apple CoreCrypto в iOS-части вашего Kotlin-приложения,
      для таких вызовов подойдёт папка [iosMain](./app/shared/src/iosMain/kotlin).
      Аналогично, для редактирования Desktop (JVM) части используйте папку
      [jvmMain](./app/shared/src/jvmMain/kotlin).

* [/core](./core/src) предназначен для кода, общедоступного для всех целей проекта.
  Важнее всего подпапка [commonMain](./core/src/commonMain/kotlin). При желании вы также можете
  добавлять код в платформо-зависимые папки.

* [/server](./server/src/main/kotlin) предназначен для серверного приложения Ktor.

### Запуск приложений

Используйте конфигурации запуска, доступные в виджете запуска на панели инструментов вашей IDE.
Также можно использовать следующие команды и параметры:

- Android-приложение: `./gradlew :app:androidApp:assembleDebug`
- Desktop-приложение:
    - Горячая перезагрузка: `./gradlew :app:desktopApp:hotRun --auto`
    - Стандартный запуск: `./gradlew :app:desktopApp:run`
- Сервер: `./gradlew :server:run`
- Web-приложение:
    - Цель Wasm (быстрее, современные браузеры): `./gradlew :app:webApp:wasmJsBrowserDevelopmentRun`
    - Генерация артефактов: `gradle :app:webApp:wasmJsBrowserDevelopmentExecutableDistribution`
      - Сгенерированные артефакты находятся в каталоге: `app/webApp/build/dist/wasmJs/developmentExecutable`
    - Цель JS (медленнее, поддержка старых браузеров): `./gradlew :app:webApp:jsBrowserDevelopmentRun`
- iOS-приложение: откройте каталог [/app/iosApp](./app/iosApp) в Xcode и запустите оттуда.

### Запуск тестов

Используйте кнопку запуска в поле слева от редактора вашей IDE или запускайте тесты через задачи Gradle:

- Тесты Android: `./gradlew :app:shared:testAndroidHostTest`
- Тесты Desktop: `./gradlew :app:shared:jvmTest`
- Тесты сервера: `./gradlew :server:test`
- Тесты Web:
    - Цель Wasm: `./gradlew :app:shared:wasmJsTest`
    - Цель JS: `./gradlew :app:shared:jsTest`
- Тесты iOS: `./gradlew :app:shared:iosSimulatorArm64Test`

---

### Добавление параметров демона Gradle

- [Указание поставщика JVM](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:specifying_a_jvm_vendor_and_version_for_the_daemon)
  `./gradlew updateDaemonJvm --jvm-version=21 --jvm-vendor=microsoft`

---

Подробнее о
[Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

Мы будем признательны за отзывы об Compose/Web и Kotlin/Wasm в публичном канале Slack
[#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
Если вы столкнётесь с проблемами, сообщите о них на [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
