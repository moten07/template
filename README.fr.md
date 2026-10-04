| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

Il s'agit d'un projet Kotlin Multiplatform ciblant Android, iOS, Web, Desktop (JVM) et Server.

* [/app/iosApp](./app/iosApp/iosApp) contient l'application iOS. Même si vous partagez votre UI avec Compose
  Multiplatform, vous avez besoin de ce point d'entrée pour votre application iOS. C'est également ici que vous
  devez ajouter le code SwiftUI de votre projet.

* [/app/shared](./app/shared/src) regroupe le code partagé entre vos applications Compose Multiplatform.
  Il contient plusieurs sous-dossiers :
    - [commonMain](./app/shared/src/commonMain/kotlin) pour le code commun à toutes les cibles.
    - Les autres dossiers sont destinés au code Kotlin compilé uniquement pour la plateforme indiquée par le
      nom du dossier. Par exemple, si vous souhaitez utiliser CoreCrypto d'Apple pour la partie iOS de votre
      application Kotlin, le dossier [iosMain](./app/shared/src/iosMain/kotlin) est l'endroit approprié pour
      de tels appels. De même, pour modifier la partie Desktop (JVM) spécifique, le dossier
      [jvmMain](./app/shared/src/jvmMain/kotlin) est l'emplacement adapté.

* [/core](./core/src) regroupe le code partagé entre toutes les cibles du projet.
  Le sous-dossier le plus important est [commonMain](./core/src/commonMain/kotlin). Si vous le souhaitez,
  vous pouvez également ajouter du code dans les dossiers spécifiques à chaque plateforme.

* [/server](./server/src/main/kotlin) contient l'application serveur Ktor.

### Exécution des applications

Utilisez les configurations d'exécution fournies par le widget d'exécution dans la barre d'outils de votre IDE.
Vous pouvez également utiliser les commandes et options suivantes :

- Application Android : `./gradlew :app:androidApp:assembleDebug`
- Application Desktop :
    - Rechargement à chaud : `./gradlew :app:desktopApp:hotRun --auto`
    - Exécution standard : `./gradlew :app:desktopApp:run`
- Serveur : `./gradlew :server:run`
- Application Web :
    - Cible Wasm (plus rapide, navigateurs modernes) : `./gradlew :app:webApp:wasmJsBrowserDevelopmentRun`
    - Générer les artefacts : `gradle :app:webApp:wasmJsBrowserDevelopmentExecutableDistribution`
      - Les artefacts générés se trouvent dans le répertoire : `app/webApp/build/dist/wasmJs/developmentExecutable`
    - Cible JS (plus lente, compatible avec les anciens navigateurs) : `./gradlew :app:webApp:jsBrowserDevelopmentRun`
- Application iOS : ouvrez le répertoire [/app/iosApp](./app/iosApp) dans Xcode et exécutez-la depuis celui-ci.

### Exécution des tests

Utilisez le bouton d'exécution dans la marge de l'éditeur de votre IDE, ou exécutez les tests via les tâches Gradle :

- Tests Android : `./gradlew :app:shared:testAndroidHostTest`
- Tests Desktop : `./gradlew :app:shared:jvmTest`
- Tests serveur : `./gradlew :server:test`
- Tests Web :
    - Cible Wasm : `./gradlew :app:shared:wasmJsTest`
    - Cible JS : `./gradlew :app:shared:jsTest`
- Tests iOS : `./gradlew :app:shared:iosSimulatorArm64Test`

---

### Ajouter des options au démon Gradle

- [Spécifier un fournisseur de JVM](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:specifying_a_jvm_vendor_and_version_for_the_daemon)
  `./gradlew updateDaemonJvm --jvm-version=21 --jvm-vendor=microsoft`

---

En savoir plus sur
[Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

Vos retours sur Compose/Web et Kotlin/Wasm sont les bienvenus sur le canal public Slack
[#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
Si vous rencontrez un problème, signalez-le sur [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
