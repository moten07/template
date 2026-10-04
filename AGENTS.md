# CODEBUDDY.md

This file provides guidance to CodeBuddy Code when working with code in this repository.

## What this project is

A **Kotlin Multiplatform (KMP) + Compose Multiplatform** template that ships one codebase to five targets:
**Android, iOS, Web (Wasm/JS), Desktop (JVM), and a Server (Ktor)**.

The project is built with the **Kotlin Toolchain** (declarative `module.yaml` / `project.yaml`, `./kotlin` CLI) —
there is no Gradle in this repository. Package namespace root is `com.example.template`. Key versions:

- Kotlin `2.4.0` (pinned in `common.module-template.yaml`), Compose Multiplatform `1.11.1`, Ktor `3.5.0`
- Android `compileSdk`/`targetSdk` = `36`, `minSdk` = `24`
- Dependency coordinates live in the root `libs.versions.toml` (the `$libs.*` catalog); shared config
  (Kotlin version, Tencent Maven mirror) lives in `common.module-template.yaml`, applied by every module.

There is **no build/lint/test script outside the Kotlin Toolchain** — everything goes through `./kotlin`.
Do not look for npm/Make/Cargo tooling. Toolchain docs: <https://kotlin-toolchain.org/dev/>

## Module layout (the big picture)

```
template/
├── core/            # Pure KMP library (kmp/lib) — shared business logic, NO Compose UI.
│                    #   Platforms: jvm, android, js, wasmJs, iosArm64, iosSimulatorArm64.
├── app/
│   ├── shared/      # Compose Multiplatform UI + app logic (kmp/lib). //core: exported.
│   │                #   Consumed by androidApp / desktopApp / webUi / iosApp.
│   ├── androidApp/  # Android application shell (android/app, MainActivity).
│   ├── desktopApp/  # JVM desktop app (jvm/app, MainKt). Compose Desktop currentOs.
│   ├── webUi/       # KMP lib (js + wasmJs) with the shared web entry UI + font resources.
│   ├── webApp/      # Web entry, Wasm (wasm-js/app). Reuses app/webUi.
│   ├── webAppJs/    # Web entry, JS (js/app, preview). Reuses app/webUi.
│   └── iosApp/      # iOS app (ios/app): src/ holds Swift + Info.plist; module.xcodeproj is
│                    #   the Xcode project (managed integration via `kotlin tool xcode-integration`).
└── server/          # Ktor JVM server (jvm/app, ApplicationKt). //core: exported. Plain JVM, no KMP.
```

Dependency direction (important — don't introduce cycles):
`server` → `core` ; `app.shared` → `core` ; `androidApp`/`desktopApp` → `app.shared` ;
`webApp`/`webAppJs` → `app/webUi` → `app.shared` ; `iosApp` → `app.shared`.
`core` must stay Compose-free so it can be reused by the server. UI code belongs in `app/shared`, not `core`.

### Source layout convention (KMP)

- `core/src/` is the only source dir in `core` (business logic shared everywhere). Platform-specific code goes
  in `src@android/` / `src@ios/` / `src@jvm/` / `src@js/` / `src@wasmJs/` when needed; tests in `test/`, `test@jvm/`, …
- `app/shared` mirrors all targets the same way (`src`, `src@android`, `src@ios`, `src@jvm`, `src@js`,
  `src@wasmJs`, plus matching `test@…` dirs). Compose resources live in `app/shared/composeResources/`.
  **Put platform-agnostic Compose code in `src/`**; only platform-specific calls (e.g. Apple Crypto on iOS)
  go in the `@platform` folders.
- JVM-only modules (`server`, `desktopApp`) use `layout: maven-like` (`src/main/kotlin`, `src/test/...`).

## Common commands

All via the Kotlin wrapper (`./kotlin` on bash, `kotlin.bat` on Windows cmd).

```bash
./kotlin show modules            # list modules (also: show dependencies / settings / checks)

# Build / run per target
./kotlin build                   # compile/link everything
./kotlin run -m desktopApp       # desktop app (also supports --compose-hot-reload)
./kotlin run -m server           # Ktor server
./kotlin run -m webApp           # Wasm in a browser (modern browsers)
# JS app (webAppJs): ./kotlin build -m webAppJs, then run the .mjs from build/tasks/
# iOS: open app/iosApp/module.xcodeproj in Xcode, or ./kotlin run -m iosApp (requires Xcode)

# Packaging
./kotlin package -m desktopApp   # executable JAR
./kotlin package -m androidApp   # signed release AAB (R8 minify/shrink automatic)
```

### Tests

```bash
./kotlin test                    # all modules
./kotlin test -m shared          # one module
./kotlin test -m server          # Ktor server tests (ktor-server-test-host)
./kotlin check                   # tests + all registered checks
```

## CI / release (`.github/workflows/`)

- `Android.yml`, `desktop.yml`, `iOS.yml` are **reusable** (`workflow_call`) builds.
- `ReleaseUploads.yml` triggers on `v*` tags, calls the Android + desktop builds, and uploads the signed AAB +
  desktop executable JAR to a GitHub release.
- Android release signing expects repo secrets: `ANDROID_KEY_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`,
  `KEY_PASSWORD` (forwarded from `ReleaseUploads.yml`). CI materializes `app/androidApp/release.keystore` and
  `app/androidApp/keystore.properties` from them (both git-ignored).
- iOS workflow references Xcode 16.4 and App Store upload secrets (`CERTIFICATES_FILE_BASE64`, `APPSTORE_*`);
  it builds `app/iosApp/module.xcodeproj` and archives/exports for TestFlight.

## Project-specific notes

- `module.yaml` values are literal — no `${...}` interpolation; module name comes from the directory name.
- The Tencent mirror (`mirrors.tencent.com`) replaces the default Maven Central/Google repos in
  `common.module-template.yaml`; bump it there if dependencies ever miss.
- Compose resources: `app/shared/composeResources` (accessors package `com.example.template.resources`,
  `exposedAccessors: true`), `app/webUi/composeResources` (package `com.example.template.webapp.resources`).
- Android signing reads `app/androidApp/keystore.properties` (+ `storeFile` keystore in the same dir).
  Generate a local keystore with `./kotlin tool generate-keystore` — never commit either file.
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
| `camerax`                                              | CameraX development guidance: recording, analyzing, editing, and composing video in Android apps via the CameraX library.                                                           |
| `display-glasses-with-jetpack-compose-glimmer`         | Build Android XR apps for display glasses using Jetpack Compose Glimmer design system.                                                                                              |
| `edge-to-edge`                                         | Migrate Compose apps to edge-to-edge; fix overlaps with nav/status bar, IME insets, system-bar legibility.                                                                          |
| `engage-sdk-integration`                               | Helps developers integrate, debug, and resolve Play Engage SDK implementation                                                                                                       |
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
