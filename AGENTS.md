# CODEBUDDY.md

This file provides guidance to CodeBuddy Code when working with code in this repository.

## What this project is

A **Kotlin Multiplatform (KMP) + Compose Multiplatform** template that ships one codebase to five targets:
**Android, iOS, Web (Wasm/JS), Desktop (JVM), and a Server (Ktor)**.

Package namespace root is `com.example.template`. Key versions (from `gradle/libs.versions.toml`):

- Kotlin `2.4.0`, Compose Multiplatform `1.11.1`, Ktor `3.5.0`, AGP `9.0.1`
- Android `compileSdk`/`targetSdk` = `36`, `minSdk` = `24`
- CI builds use **Microsoft JDK 21** (see `gradle.properties` / workflows). Configure the Gradle daemon JVM with
  `./gradlew updateDaemonJvm --jvm-version=21 --jvm-vendor=microsoft` if needed.

There is **no build/lint/test script outside Gradle** — everything goes through `./gradlew`. Do not look for
npm/Make/Cargo tooling.

## Module layout (the big picture)

```
template/
├── core/            # Pure KMP library — shared business logic, NO Compose UI.
│                   #   Targets: android, ios, jvm, js, wasmJs. Depends on nothing here.
├── app/
│   ├── shared/      # Compose Multiplatform UI + app logic. api(projects.core).
│   │               #   Produces the iOS framework ("Shared", static) and is consumed
│   │               #   by androidApp / desktopApp / webApp.
│   ├── androidApp/  # Android application shell (MainActivity). implementation(projects.app.shared).
│   ├── desktopApp/  # JVM desktop app (MainKt). Compose Desktop currentOs. Packages Dmg/Msi/AppImage.
│   ├── webApp/      # Web entry. js + wasmJs executables, reuses app.shared UI.
│   └── iosApp/      # Xcode project (Swift/UIKit bridge). NOT built by Gradle — open in Xcode.
└── server/          # Ktor JVM server (ApplicationKt). api(projects.core). Plain Kotlin/JVM, no KMP.
```

Dependency direction (important — don't introduce cycles):
`server` → `core` ; `app.shared` → `core` ; `androidApp`/`desktopApp`/`webApp` → `app.shared`.
`core` must stay Compose-free so it can be reused by the server. UI code belongs in `app/shared`, not `core`.

### Source-set convention (KMP)

- `core/src/commonMain` is the only source set in `core` (business logic shared everywhere). Platform-specific code goes
  in `androidMain` / `iosMain` / `jvmMain` / `jsMain` / `wasmJsMain` when needed.
- `app/shared/src` mirrors all targets: `commonMain`, `androidMain`, `iosMain`, `jvmMain`, `jsMain`, `wasmJsMain`, plus
  the matching `*Test` sets. **Put platform-agnostic Compose code in `commonMain`**; only platform-specific calls (e.g.
  Apple Crypto on iOS) go in the named target folders.

## Common commands

All via Gradle wrapper (`./gradlew` on bash/Git Bash, `gradlew.bat` on Windows cmd).

```shell
gradle :wrapper --gradle-version 9.7.1 --distribution-type all
```

```bash
# Build / run per target
./gradlew :app:androidApp:assembleDebug
./gradlew :app:desktopApp:run                      # standard run
./gradlew :app:desktopApp:hotRun --auto            # hot reload
./gradlew :server:run
./gradlew :app:webApp:wasmJsBrowserDevelopmentRun  # Wasm (modern browsers)
./gradlew :app:webApp:jsBrowserDevelopmentRun      # JS (older browsers)
# iOS: open app/iosApp in Xcode and run from there (not a Gradle task)

# Web artifact output
gradle :app:webApp:wasmJsBrowserDevelopmentExecutableDistribution
#   -> app/webApp/build/dist/wasmJs/developmentExecutable
```

### Tests (run a single test / module)

```bash
./gradlew :app:shared:testAndroidHostTest   # Android (host test)
./gradlew :app:shared:jvmTest                # Desktop/JVM
./gradlew :server:test                      # Ktor server (JUnit via ktor-server-test-host)
./gradlew :app:shared:wasmJsTest             # Web Wasm
./gradlew :app:shared:jsTest                 # Web JS
./gradlew :app:shared:iosSimulatorArm64Test # iOS
```

To run a **single test class/method**, append `--tests "com.example.template.MyTest"` (works for JVM/JUnit targets:
`:server:test`, `:app:shared:jvmTest`). For KMP, you can also scope by the Gradle task + `--tests`.

## CI / release (`.github/workflows/`)

- `Android.yml`, `desktop.yml`, `iOS.yml` are **reusable** (`workflow_call`) builds.
- `ReleaseUploads.yml` triggers on `v*` tags, calls the three above, and uploads APK + AppImage to a GitHub release.
- Android release signing expects repo secrets: `ANDROID_KEY_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`
  (forwarded from `ReleaseUploads.yml`).
- iOS workflow references Xcode 16.4 and App Store upload secrets (`CERTIFICATES_FILE_BASE64`, `APPSTORE_*`).

## Project-specific notes

- `gradle.properties` enables **configuration cache** and **build cache** — treat Gradle config changes carefully, as
  cache can mask issues. Daemon JVM args: `-Xmx4096M`.
- `local.properties` holds the local Android SDK path (`sdk.dir`) and is **git-ignored**; do not commit it.
- `settings.gradle.kts` uses `enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")` — reference sibling modules via
  `projects.<path>` (e.g. `projects.core`, `projects.app.shared`) in `build.gradle.kts`.
- Compose resources live in `app/shared/src/commonMain/composeResources`; generated accessors appear under
  `app/shared/build/generated/compose/resourceGenerator/` (do not edit generated files).
- Bundled agent skills live in `.agents/skills/` — these are reference material for CodeBuddy, not project source.

## Bundled agent skills (`.agents/skills/`)

Reference skills that can be loaded on demand. Use the `skill` tool when a task matches one of these.

| Skill                                                  | Purpose                                                                                                                                                                             |
|--------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `adaptive`                                             | Make/update UI to adapt across Android devices (phones, tablets, foldables, TV, Auto, XR) — window sizes, pointing/keyboard input, multi-pane Navigation3, adaptive nav rails/bars. |
| `agp-9-upgrade`                                        | Migrate a plain Android project to Android Gradle Plugin 9. (Not for KMP projects.)                                                                                                 |
| `android-cli`                                          | Install/use the `android` CLI: create projects, run apps, manage AVDs, SDK components, look up docs, discover/install skills.                                                       |
| `android-intent-security`                              | Audit `AndroidManifest.xml` components and incoming-Intent handling to prevent Intent Redirection and unauthorized access.                                                          |
| `appfunctions`                                         | Identify app workflows and generate Kotlin to expose them as Android AppFunctions; refine KDoc for agent discoverability.                                                           |
| `camera1-to-camerax`                                   | Migrate legacy Camera1 / raw Camera2 implementations to lifecycle-aware CameraX.                                                                                                    |
| `camerax`                                              | CameraX development guidance: recording lifecycles, low-level hardware interop, ML Kit / Media3 effects.                                                                            |
| `display-glasses-with-jetpack-compose-glimmer`         | Build Android XR apps for display glasses using Jetpack Compose Glimmer design system.                                                                                              |
| `edge-to-edge`                                         | Migrate Compose apps to edge-to-edge; fix overlaps with nav/status bars, IME insets, system-bar legibility.                                                                         |
| `engage-sdk-integration`                               | Integrate/debug the Play Engage SDK; map data classes to entities, fix SDK errors.                                                                                                  |
| `kotlin-backend-jpa-entity-mapping`                    | Correct Kotlin JPA/Hibernate entity modeling; avoid N+1, LazyInitializationException, broken equals/hashCode.                                                                       |
| `kotlin-tooling-agp9-migration`                        | Migrate KMP projects to AGP 9.0+ (plugin replacement, module splitting, DSL/default structure).                                                                                     |
| `kotlin-tooling-cocoapods-spm-migration`               | Migrate KMP from CocoaPods to Swift Package Manager (`swiftPMDependencies`).                                                                                                        |
| `kotlin-tooling-immutable-collections-0-5-x-migration` | Migrate `kotlinx.collections.immutable` 0.3/0.4 → 0.5.x (renamed copy-returning methods).                                                                                           |
| `kotlin-tooling-java-to-kotlin`                        | Convert Java to idiomatic Kotlin, framework-aware (Spring, Lombok, Hibernate, Dagger/Hilt, etc.).                                                                                   |
| `kotlin-tooling-native-build-performance`              | Diagnose/fix slow Kotlin/Native compile & link (iOS/shared frameworks, CI caching).                                                                                                 |
| `migrate-xml-views-to-jetpack-compose`                 | Structured workflow to migrate Android XML Views to Jetpack Compose with interop.                                                                                                   |
| `navigation-3`                                         | Install/migrate to Jetpack Navigation 3: deep links, multiple backstacks, scenes, conditional navigation, Hilt/ViewModel.                                                           |
| `perfetto-sql`                                         | Translate natural-language intents into Perfetto SQL and run against a local trace.                                                                                                 |
| `perfetto-trace-analysis`                              | Analyze Perfetto traces to find root cause of latency/memory/jank issues.                                                                                                           |
| `play-billing-library-version-upgrade`                 | Upgrade/migrate an app to the latest Google Play Billing Library.                                                                                                                   |
| `r8-analyzer`                                          | Analyze build files & R8 keep rules; remove redundant/broad rules, troubleshoot Proguard.                                                                                           |
| `styles`                                               | Integrate Jetpack Compose Styles API; make components styleable, migrate hardcoded params.                                                                                          |
| `testing-setup`                                        | Design/set up Android testing: unit, UI, screenshot, end-to-end harnesses.                                                                                                          |
| `verified-email`                                       | Implement OTP-less verified email via Android Credential Manager API.                                                                                                               |
| `wear-compose-m3`                                      | Wear OS Compose Material3 guidance: AppScaffold, TransformingLazyColumn, ambient mode, migration.                                                                                   |

## Reference

The README (`README.md`) at the repo root is the authoritative quickstart for running each target and is kept in sync
with the commands above. Consult it first for end-user run instructions.
