| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

Este es un proyecto Kotlin Multiplatform que tiene como objetivo Android, iOS, Web, Desktop (JVM) y Server.

* [/app/iosApp](./app/iosApp/iosApp) contiene una aplicación iOS. Incluso si compartes tu UI con Compose
  Multiplatform, necesitas este punto de entrada para tu app iOS. Aquí también es donde debes añadir
  el código SwiftUI de tu proyecto.

* [/app/shared](./app/shared/src) es para el código que se compartirá entre tus aplicaciones Compose Multiplatform.
  Contiene varias subcarpetas:
    - [commonMain](./app/shared/src/commonMain/kotlin) es para el código común a todos los objetivos.
    - Las demás carpetas son para código Kotlin que se compilará solo para la plataforma indicada en el nombre
      de la carpeta. Por ejemplo, si quieres usar CoreCrypto de Apple en la parte iOS de tu app Kotlin,
      la carpeta [iosMain](./app/shared/src/iosMain/kotlin) es el lugar adecuado para tales llamadas.
      De manera similar, si quieres editar la parte específica de Desktop (JVM), la carpeta
      [jvmMain](./app/shared/src/jvmMain/kotlin) es la ubicación apropiada.

* [/core](./core/src) es para el código que se compartirá entre todos los objetivos del proyecto.
  La subcarpeta más importante es [commonMain](./core/src/commonMain/kotlin). Si lo prefieres, también
  puedes añadir código a las carpetas específicas de plataforma aquí.

* [/server](./server/src/main/kotlin) es para la aplicación de servidor Ktor.

### Ejecutar las aplicaciones

Usa las configuraciones de ejecución proporcionadas por el widget de ejecución en la barra de herramientas de tu IDE.
También puedes usar estos comandos y opciones:

- Aplicación Android: `./gradlew :app:androidApp:assembleDebug`
- Aplicación Desktop:
    - Recarga en caliente: `./gradlew :app:desktopApp:hotRun --auto`
    - Ejecución estándar: `./gradlew :app:desktopApp:run`
- Servidor: `./gradlew :server:run`
- Aplicación Web:
    - Objetivo Wasm (más rápido, navegadores modernos): `./gradlew :app:webApp:wasmJsBrowserDevelopmentRun`
    - Generar artefactos: `gradle :app:webApp:wasmJsBrowserDevelopmentExecutableDistribution`
      - Los artefactos generados están en el directorio: `app/webApp/build/dist/wasmJs/developmentExecutable`
    - Objetivo JS (más lento, soporta navegadores antiguos): `./gradlew :app:webApp:jsBrowserDevelopmentRun`
- Aplicación iOS: abre el directorio [/app/iosApp](./app/iosApp) en Xcode y ejecútala desde allí.

### Ejecutar las pruebas

Usa el botón de ejecución en la barra lateral del editor de tu IDE, o ejecuta las pruebas con tareas de Gradle:

- Pruebas Android: `./gradlew :app:shared:testAndroidHostTest`
- Pruebas Desktop: `./gradlew :app:shared:jvmTest`
- Pruebas del servidor: `./gradlew :server:test`
- Pruebas Web:
    - Objetivo Wasm: `./gradlew :app:shared:wasmJsTest`
    - Objetivo JS: `./gradlew :app:shared:jsTest`
- Pruebas iOS: `./gradlew :app:shared:iosSimulatorArm64Test`

---

### Añadir opciones del Gradle Daemon

- [Especificar un proveedor de JVM](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:specifying_a_jvm_vendor_and_version_for_the_daemon)
  `./gradlew updateDaemonJvm --jvm-version=21 --jvm-vendor=microsoft`

---

Conoce más
sobre [Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

Agradecemos tus comentarios sobre Compose/Web y Kotlin/Wasm en el canal público de Slack
[#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
Si encuentras algún problema, repórtalo en [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
