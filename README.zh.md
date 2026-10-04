| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

这是一个 Kotlin Multiplatform 项目，目标平台包括 Android、iOS、Web、Desktop（JVM）、Server。

* [/app/iosApp](./app/iosApp/iosApp) 包含 iOS 应用。即使你使用 Compose Multiplatform 共享 UI，
  仍然需要这个入口作为 iOS 应用的起点。你也可以在这里添加项目的 SwiftUI 代码。

* [/app/shared](./app/shared/src) 用于跨 Compose Multiplatform 应用共享的代码。
  它包含若干子文件夹：
    - [commonMain](./app/shared/src/commonMain/kotlin) 用于所有平台共享的代码。
    - 其他文件夹用于只针对文件夹名称所示平台编译的 Kotlin 代码。
      例如，如果你想在 Kotlin 应用的 iOS 部分使用 Apple 的 CoreCrypto，
      [iosMain](./app/shared/src/iosMain/kotlin) 文件夹就是放置此类调用的合适位置。
      同理，如果你想编辑 Desktop（JVM）专属部分，[jvmMain](./app/shared/src/jvmMain/kotlin)
      文件夹则是相应的位置。

* [/core](./core/src) 用于项目中所有目标平台共享的代码。
  最重要的子文件夹是 [commonMain](./core/src/commonMain/kotlin)。如果愿意，
  你也可以在此添加平台专属文件夹中的代码。

* [/server](./server/src/main/kotlin) 用于 Ktor 服务端应用。

### 运行应用

使用 IDE 工具栏中运行控件提供的运行配置。你也可以直接使用以下命令和参数：

- Android 应用：`./gradlew :app:androidApp:assembleDebug`
- Desktop 应用：
    - 热重载：`./gradlew :app:desktopApp:hotRun --auto`
    - 标准运行：`./gradlew :app:desktopApp:run`
- 服务端：`./gradlew :server:run`
- Web 应用：
    - Wasm 目标（更快，现代浏览器）：`./gradlew :app:webApp:wasmJsBrowserDevelopmentRun`
    - 生成产物：`gradle :app:webApp:wasmJsBrowserDevelopmentExecutableDistribution`
      - 生成的产物位于目录：`app/webApp/build/dist/wasmJs/developmentExecutable`
    - JS 目标（较慢，支持旧版浏览器）：`./gradlew :app:webApp:jsBrowserDevelopmentRun`
- iOS 应用：在 Xcode 中打开 [/app/iosApp](./app/iosApp) 目录，并从那里运行。

### 运行测试

使用 IDE 编辑器行号槽中的运行按钮，或使用 Gradle 任务运行测试：

- Android 测试：`./gradlew :app:shared:testAndroidHostTest`
- Desktop 测试：`./gradlew :app:shared:jvmTest`
- 服务端测试：`./gradlew :server:test`
- Web 测试：
    - Wasm 目标：`./gradlew :app:shared:wasmJsTest`
    - JS 目标：`./gradlew :app:shared:jsTest`
- iOS 测试：`./gradlew :app:shared:iosSimulatorArm64Test`

---

### 添加 Gradle Daemon 选项

- [指定 JVM 厂商](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:specifying_a_jvm_vendor_and_version_for_the_daemon)
  `./gradlew updateDaemonJvm --jvm-version=21 --jvm-vendor=microsoft`

---

了解更多关于
[Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html)、
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform)、
[Kotlin/Wasm](https://kotl.in/wasm/)…

如果你对 Compose/Web 和 Kotlin/Wasm 有任何反馈，欢迎在公共 Slack 频道
[#compose-web](https://slack-chats.kotlinlang.org/c/compose-web) 中交流。
如果遇到任何问题，请在 [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP) 上反馈。
