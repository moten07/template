| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

Este es un proyecto Kotlin Multiplatform que tiene como objetivo Android, iOS, Web, Desktop (JVM) y Server,
construido con el **Kotlin Toolchain** (`project.yaml` + `module.yaml`, sin Gradle).

* [/app/iosApp](./app/iosApp/src) contiene una aplicación iOS. Incluso si compartes tu UI con Compose
  Multiplatform, necesitas este punto de entrada para tu app iOS. Aquí también es donde debes añadir
  el código SwiftUI de tu proyecto.

* [/app/shared](./app/shared/src) es para el código que se compartirá entre tus aplicaciones Compose Multiplatform.
  Contiene varias subcarpetas:
    - [src](./app/shared/src) es para el código común a todos los objetivos.
    - Las demás carpetas `src@<platform>` son para código Kotlin que se compilará solo para la plataforma indicada
      en el nombre de la carpeta. Por ejemplo, si quieres usar CoreCrypto de Apple en la parte iOS de tu app Kotlin,
      la carpeta [src@ios](./app/shared/src@ios) es el lugar adecuado para tales llamadas.
      De manera similar, si quieres editar la parte específica de Desktop (JVM), la carpeta
      [src@jvm](./app/shared/src@jvm) es la ubicación apropiada.

* [/core](./core/src) es para el código que se compartirá entre todos los objetivos del proyecto.
  La subcarpeta más importante es [src](./core/src). Si lo prefieres, también
  puedes añadir código a las carpetas específicas de plataforma (`src@jvm`, …) aquí.

* [/server](./server/src/main/kotlin) es para la aplicación de servidor Ktor.

### Ejecutar las aplicaciones

Usa las configuraciones de ejecución proporcionadas por el widget de ejecución en la barra de herramientas de tu IDE.
También puedes usar estos comandos y opciones (todos vía el wrapper `./kotlin` del repositorio):

- Aplicación Android: `./kotlin package -m androidApp` (AAB de release firmado)
- Aplicación Desktop:
    - Recarga en caliente: `./kotlin run -m desktopApp --compose-hot-reload` (o desde el IDE)
    - Ejecución estándar: `./kotlin run -m desktopApp`
- Servidor: `./kotlin run -m server`
- Aplicación Web:
    - Objetivo Wasm (más rápido, navegadores modernos): `./kotlin run -m webApp`
    - Generar artefactos: `./kotlin build -m webApp`
      - Los artefactos generados están bajo `app/webApp/build/tasks/` (el comando imprime la ruta)
    - Objetivo JS (más lento, soporta navegadores antiguos): `./kotlin build -m webAppJs`, luego abre el `.mjs`
      generado en `app/webAppJs/build/tasks/` en una página del navegador (la CLI aún no puede lanzar apps JS)
- Aplicación iOS: abre [/app/iosApp/module.xcodeproj](./app/iosApp/module.xcodeproj) en Xcode y ejecútala desde
  allí, o usa `./kotlin run -m iosApp`.

### Ejecutar las pruebas

Usa el botón de ejecución en la barra lateral del editor de tu IDE, o ejecuta las pruebas con la CLI:

- Todas las pruebas: `./kotlin test`
- Un solo módulo: `./kotlin test -m shared`, `./kotlin test -m server`, …

Las pruebas y comprobaciones de todos los módulos también pueden ejecutarse con `./kotlin check`.

---

Conoce más
sobre [Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/),
el [Kotlin Toolchain](https://kotlin-toolchain.org/dev/)…

Agradecemos tus comentarios sobre Compose/Web y Kotlin/Wasm en el canal público de Slack
[#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
Si encuentras algún problema, repórtalo en [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
