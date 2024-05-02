plugins {
  id("java-gradle-plugin")
  `kotlin-dsl`

  alias(libs.plugins.buildconfig)
}

// use overridable options for publishing information (see gradle.properties)
// in CI for example, the "SNAPSHOT" variant may be added to the version
group = property("buildkit.group").toString()
version = property("buildkit.version").toString()

// resolve the name and full ID for the project plugin
val projectPluginId = "$group.${property("buildkit.project-plugin-id")}"
val projectPluginName = property("buildkit.project-plugin-name").toString()

// resolve the name and full ID for the settings plugin
val settingsPluginId = "$group.${property("buildkit.settings-plugin-id")}"
val settingsPluginName = property("buildkit.settings-plugin-name").toString()

gradlePlugin {
  plugins.create(projectPluginName) {
    id = projectPluginId
    implementationClass = "io.github.darvld.buildkit.BuildkitPlugin"
  }

  plugins.create(settingsPluginName) {
    id = settingsPluginId
    implementationClass = "io.github.darvld.buildkit.BuildkitSettingsPlugin"
  }
}

kotlin {
  explicitApi()
}

dependencies {
  testImplementation(libs.junit.params)
  testImplementation(gradleTestKit())
  testImplementation(kotlin("test"))
}

// values used during env-based option tests
val testEnvKey = "TEST_ENV"
val testEnvValue = "TEST_ENV_VALUE"

buildConfig {
  packageName = "io.github.darvld.buildkit"

  sourceSets.named("test") {
    className = "TestConstants"

    buildConfigField("String", "TEST_ENV_KEY", "\"$testEnvKey\"")
    buildConfigField("String", "TEST_ENV_VALUE", "\"$testEnvValue\"")
  }
}

tasks.test {
  useJUnitPlatform()

  // set the env expected by some test cases
  environment(testEnvKey, testEnvValue)
}
