import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_25
    }
}

android {
    namespace = libs.versions.pkg.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.pkg.get()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

	signingConfigs {
		create("release") {
			enableV1Signing = true
			enableV2Signing = true
			enableV3Signing = true
			enableV4Signing = true
		}
	}

	buildTypes {
        getByName("release") {
            isMinifyEnabled = true
			isShrinkResources = true
			signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }
}

dependencies {
	implementation(projects.app.shared)

	implementation(libs.androidx.activity.compose)

	implementation(libs.compose.uiToolingPreview)
	debugImplementation(libs.compose.uiTooling)
}
