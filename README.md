| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop (JVM), Server, built with the
**Kotlin Toolchain** (`project.yaml` + `module.yaml`, no Gradle).

* [/app/iosApp](./app/iosApp/src) contains an iOS application. Even if you’re sharing your UI with Compose
  Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/app/shared](./app/shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
    - [src](./app/shared/src) is for code that’s common for all targets.
    - Other `src@<platform>` folders are for Kotlin code that will be compiled for only the platform indicated in
      the folder name. For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      the [src@ios](./app/shared/src@ios) folder would be the right place for such calls.
      Similarly, if you want to edit the Desktop (JVM) specific part, the [src@jvm](./app/shared/src@jvm)
      folder is the appropriate location.

* [/core](./core/src) is for the code that will be shared between all targets in the project.
  The most important subfolder is [src](./core/src). If preferred, you
  can add code to the platform-specific folders (`src@jvm`, …) here too.

* [/server](./server/src/main/kotlin) is for the Ktor server application.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and
options (all via the checked-in `./kotlin` wrapper):

- Android app: `./kotlin package -m androidApp` (signed release AAB)
- Desktop app:
    - Hot reload: `./kotlin run -m desktopApp --compose-hot-reload` (or from the IDE)
    - Standard run: `./kotlin run -m desktopApp`
- Server: `./kotlin run -m server`
- Web app:
    - Wasm target (faster, modern browsers): `./kotlin run -m webApp`
    + Generate artifacts: `./kotlin build -m webApp`
      +  The generated artifacts are under `app/webApp/build/tasks/` (the command prints the path)
    - JS target (slower, supports older browsers): `./kotlin build -m webAppJs`, then open the produced
      `.mjs` from `app/webAppJs/build/tasks/` in a browser page (JS apps cannot be launched by the CLI yet)
- iOS app: open [/app/iosApp/module.xcodeproj](./app/iosApp/module.xcodeproj) in Xcode and run it from there,
  or `./kotlin run -m iosApp`.

### Running tests

Use the run button in your IDE's editor gutter, or run tests via the CLI:

- All tests: `./kotlin test`
- One module: `./kotlin test -m shared`, `./kotlin test -m server`, …

Tests and checks for every module can also be run together with `./kotlin check`.

---

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/),
the [Kotlin Toolchain](https://kotlin-toolchain.org/dev/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack
channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
