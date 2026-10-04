rootProject.name = "template"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
	repositories {
		maven {
			name = "tencentGoogle"
			url = uri("https://mirrors.tencent.com/nexus/repository/maven-public/")
			content {
				includeGroupAndSubgroups("androidx")
				includeGroupAndSubgroups("com.android")
				includeGroupAndSubgroups("com.google")
			}
		}
		google {
			mavenContent {
				includeGroupAndSubgroups("androidx")
				includeGroupAndSubgroups("com.android")
				includeGroupAndSubgroups("com.google")
			}
		}
		mavenCentral()
		gradlePluginPortal()
		maven {
			name = "tencent"
			url = uri("https://mirrors.tencent.com/nexus/repository/gradle-plugins/")
		}
	}
}

dependencyResolutionManagement {
	repositories {
		maven {
			name = "tencent"
			url = uri("https://mirrors.tencent.com/nexus/repository/maven-public/")
		}
		google {
			mavenContent {
				includeGroupAndSubgroups("androidx")
				includeGroupAndSubgroups("com.android")
				includeGroupAndSubgroups("com.google")
			}
		}
		mavenCentral()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":app:androidApp")
include(":app:desktopApp")
include(":app:shared")
include(":app:webApp")
include(":core")
include(":server")
