| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

这是一个 Kotlin Multiplatform 项目，目标平台包括 Android、iOS、Web、Desktop（JVM）、Server，
使用 **Kotlin Toolchain** 构建（`project.yaml` + `module.yaml`，无 Gradle）。

* [/app/iosApp](./app/iosApp/src) 包含 iOS 应用。即使你使用 Compose Multiplatform 共享 UI，
  仍然需要这个入口作为 iOS 应用的起点。你也可以在这里添加项目的 SwiftUI 代码。

* [/app/shared](./app/shared/src) 用于跨 Compose Multiplatform 应用共享的代码。
  它包含若干子文件夹：
    - [src](./app/shared/src) 用于所有平台共享的代码。
    - 其他 `src@<platform>` 文件夹用于只针对文件夹名称所示平台编译的 Kotlin 代码。
      例如，如果你想在 Kotlin 应用的 iOS 部分使用 Apple 的 CoreCrypto，
      [src@ios](./app/shared/src@ios) 文件夹就是放置此类调用的合适位置。
      同理，如果你想编辑 Desktop（JVM）专属部分，[src@jvm](./app/shared/src@jvm)
      文件夹则是相应的位置。

* [/core](./core/src) 用于项目中所有目标平台共享的代码。
  最重要的子文件夹是 [src](./core/src)。如果愿意，
  你也可以在此添加平台专属文件夹（`src@jvm` 等）中的代码。

* [/server](./server/src/main/kotlin) 用于 Ktor 服务端应用。

### 运行应用

使用 IDE 工具栏中运行控件提供的运行配置。你也可以直接使用以下命令和参数（均通过仓库内的 `./kotlin` 包装脚本）：

- Android 应用：`./kotlin package -m androidApp`（签名 release AAB）
- Desktop 应用：
    - 热重载：`./kotlin run -m desktopApp --compose-hot-reload`（或在 IDE 中运行）
    - 标准运行：`./kotlin run -m desktopApp`
- 服务端：`./kotlin run -m server`
- Web 应用：
    - Wasm 目标（更快，现代浏览器）：`./kotlin run -m webApp`
    - 生成产物：`./kotlin build -m webApp`
      - 生成的产物位于 `app/webApp/build/tasks/` 目录（命令会打印具体路径）
    - JS 目标（较慢，支持旧版浏览器）：`./kotlin build -m webAppJs`，
      然后在浏览器页面中打开 `app/webAppJs/build/tasks/` 下生成的 `.mjs`（CLI 暂不能直接启动 JS 应用）
- iOS 应用：在 Xcode 中打开 [/app/iosApp/module.xcodeproj](./app/iosApp/module.xcodeproj) 并从那里运行，
  或执行 `./kotlin run -m iosApp`。

### 运行测试

使用 IDE 编辑器行号槽中的运行按钮，或通过 CLI 运行测试：

- 全部测试：`./kotlin test`
- 单个模块：`./kotlin test -m shared`、`./kotlin test -m server` …

所有模块的测试与检查也可以用 `./kotlin check` 一起运行。

---

了解更多关于
[Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html)、
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform)、
[Kotlin/Wasm](https://kotl.in/wasm/)、
[Kotlin Toolchain](https://kotlin-toolchain.org/dev/)…

如果你对 Compose/Web 和 Kotlin/Wasm 有任何反馈，欢迎在公共 Slack 频道
[#compose-web](https://slack-chats.kotlinlang.org/c/compose-web) 中交流。
如果遇到任何问题，请在 [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP) 上反馈。
