| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

Это проект Kotlin Multiplatform с поддержкой платформ Android, iOS, Web, Desktop (JVM) и Server,
собираемый с помощью **Kotlin Toolchain** (`project.yaml` + `module.yaml`, без Gradle).

* [/app/iosApp](./app/iosApp/src) содержит iOS-приложение. Даже если вы используете общий UI на базе Compose
  Multiplatform, вам всё равно нужна эта точка входа для iOS-приложения. Здесь же следует добавлять код на SwiftUI.

* [/app/shared](./app/shared/src) предназначен для кода, который используется совместно во всех приложениях
  Compose Multiplatform. Он содержит несколько подпапок:
    - [src](./app/shared/src) — код, общий для всех платформ.
    - Остальные папки `src@<platform>` — для Kotlin-кода, который компилируется только для платформы, указанной
      в названии папки. Например, если вы хотите использовать Apple CoreCrypto в iOS-части вашего Kotlin-приложения,
      для таких вызовов подойдёт папка [src@ios](./app/shared/src@ios).
      Аналогично, для редактирования Desktop (JVM) части используйте папку
      [src@jvm](./app/shared/src@jvm).

* [/core](./core/src) предназначен для кода, общедоступного для всех целей проекта.
  Важнее всего подпапка [src](./core/src). При желании вы также можете
  добавлять код в платформо-зависимые папки (`src@jvm`, …).

* [/server](./server/src/main/kotlin) предназначен для серверного приложения Ktor.

### Запуск приложений

Используйте конфигурации запуска, доступные в виджете запуска на панели инструментов вашей IDE.
Также можно использовать следующие команды и параметры (все — через врапер `./kotlin` из репозитория):

- Android-приложение: `./kotlin package -m androidApp` (подписанный release AAB)
- Desktop-приложение:
    - Горячая перезагрузка: `./kotlin run -m desktopApp --compose-hot-reload` (или из IDE)
    - Стандартный запуск: `./kotlin run -m desktopApp`
- Сервер: `./kotlin run -m server`
- Web-приложение:
    - Цель Wasm (быстрее, современные браузеры): `./kotlin run -m webApp`
    - Генерация артефактов: `./kotlin build -m webApp`
      - Сгенерированные артефакты находятся в `app/webApp/build/tasks/` (команда выводит путь)
    - Цель JS (медленнее, поддержка старых браузеров): `./kotlin build -m webAppJs`, затем откройте
      полученный `.mjs` из `app/webAppJs/build/tasks/` в браузере (CLI пока не может запускать JS-приложения)
- iOS-приложение: откройте [/app/iosApp/module.xcodeproj](./app/iosApp/module.xcodeproj) в Xcode и запустите оттуда,
  либо выполните `./kotlin run -m iosApp`.

### Запуск тестов

Используйте кнопку запуска в поле слева от редактора вашей IDE или запускайте тесты через CLI:

- Все тесты: `./kotlin test`
- Один модуль: `./kotlin test -m shared`, `./kotlin test -m server`, …

Тесты и проверки всех модулей можно запустить вместе командой `./kotlin check`.

---

Подробнее о
[Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/),
[Kotlin Toolchain](https://kotlin-toolchain.org/dev/)…

Мы будем признательны за отзывы об Compose/Web и Kotlin/Wasm в публичном канале Slack
[#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
Если вы столкнётесь с проблемами, сообщите о них на [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
