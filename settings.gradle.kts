@file: Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "buildkit"

include(
  "packages:buildkit-plugin",
)

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    mavenLocal()
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
    google()
    mavenLocal()
  }
}
