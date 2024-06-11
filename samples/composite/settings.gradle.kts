rootProject.name = "composite-sample"

include("app")

// use the plugin built by the main project; this reverse
// dependency is required for the plugin to be present in
// the build classpath
includeBuild("../..")

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
