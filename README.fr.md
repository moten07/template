| [English](README.md) |  [中文](README.zh.md) | [Français](README.fr.md) | [Русский](README.ru.md) | [Español](README.es.md) |

Il s'agit d'un projet Kotlin Multiplatform ciblant Android, iOS, Web, Desktop (JVM) et Server, construit avec le
**Kotlin Toolchain** (`project.yaml` + `module.yaml`, sans Gradle).

* [/app/iosApp](./app/iosApp/src) contient l'application iOS. Même si vous partagez votre UI avec Compose
  Multiplatform, vous avez besoin de ce point d'entrée pour votre application iOS. C'est également ici que vous
  devez ajouter le code SwiftUI de votre projet.

* [/app/shared](./app/shared/src) regroupe le code partagé entre vos applications Compose Multiplatform.
  Il contient plusieurs sous-dossiers :
    - [src](./app/shared/src) pour le code commun à toutes les cibles.
    - Les autres dossiers `src@<platform>` sont destinés au code Kotlin compilé uniquement pour la plateforme
      indiquée par le nom du dossier. Par exemple, si vous souhaitez utiliser CoreCrypto d'Apple pour la partie
      iOS de votre application Kotlin, le dossier [src@ios](./app/shared/src@ios) est l'endroit approprié pour
      de tels appels. De même, pour modifier la partie Desktop (JVM) spécifique, le dossier
      [src@jvm](./app/shared/src@jvm) est l'emplacement adapté.

* [/core](./core/src) regroupe le code partagé entre toutes les cibles du projet.
  Le sous-dossier le plus important est [src](./core/src). Si vous le souhaitez,
  vous pouvez également ajouter du code dans les dossiers spécifiques à chaque plateforme (`src@jvm`, …).

* [/server](./server/src/main/kotlin) contient l'application serveur Ktor.

### Exécution des applications

Utilisez les configurations d'exécution fournies par le widget d'exécution dans la barre d'outils de votre IDE.
Vous pouvez également utiliser les commandes et options suivantes (via le wrapper `./kotlin` du dépôt) :

- Application Android : `./kotlin package -m androidApp` (AAB de release signé)
- Application Desktop :
    - Rechargement à chaud : `./kotlin run -m desktopApp --compose-hot-reload` (ou depuis l'IDE)
    - Exécution standard : `./kotlin run -m desktopApp`
- Serveur : `./kotlin run -m server`
- Application Web :
    - Cible Wasm (plus rapide, navigateurs modernes) : `./kotlin run -m webApp`
    - Générer les artefacts : `./kotlin build -m webApp`
      - Les artefacts générés se trouvent sous `app/webApp/build/tasks/` (la commande affiche le chemin)
    - Cible JS (plus lente, compatible avec les anciens navigateurs) : `./kotlin build -m webAppJs`, puis ouvrez
      le `.mjs` produit dans `app/webAppJs/build/tasks/` dans une page navigateur (la CLI ne peut pas encore
      lancer les applications JS)
- Application iOS : ouvrez [/app/iosApp/module.xcodeproj](./app/iosApp/module.xcodeproj) dans Xcode et exécutez-la
  depuis celui-ci, ou `./kotlin run -m iosApp`.

### Exécution des tests

Utilisez le bouton d'exécution dans la marge de l'éditeur de votre IDE, ou exécutez les tests via la CLI :

- Tous les tests : `./kotlin test`
- Un seul module : `./kotlin test -m shared`, `./kotlin test -m server`, …

Les tests et vérifications de tous les modules peuvent aussi être lancés avec `./kotlin check`.

---

En savoir plus sur
[Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/),
le [Kotlin Toolchain](https://kotlin-toolchain.org/dev/)…

Vos retours sur Compose/Web et Kotlin/Wasm sont les bienvenus sur le canal public Slack
[#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
Si vous rencontrez un problème, signalez-le sur [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
