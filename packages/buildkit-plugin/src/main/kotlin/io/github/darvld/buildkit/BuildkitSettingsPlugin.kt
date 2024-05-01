package io.github.darvld.buildkit

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create
import java.io.File
import java.util.*

public class BuildkitSettingsPlugin : Plugin<Settings> {
  override fun apply(target: Settings) {
    target.extensions.create<OptionsExtension>(OPTIONS_EXTENSION, target.collectOptions())
  }

  private fun Settings.collectOptions(): Map<String, String> = buildMap {
    val namespace = rootProject.name

    // basic project gradle.properties (lowest priority)
    collectOptions(layout.rootDirectory.file(GRADLE_PROPERTIES).asFile, namespace)

    // from gradle.properties in the user directory (can override source-controlled property files)
    collectOptions(gradle.gradleUserHomeDir.resolve(GRADLE_PROPERTIES), namespace)

    // from local.properties in the project directory (overrides user-level properties)
    collectOptions(layout.rootDirectory.file(LOCAL_PROPERTIES).asFile, namespace)

    // from environment variables (highest priority)
    System.getenv().forEach { (key, value) ->
      val normalized = key.lowercase().replace("_", ".")
      if (normalized.startsWith(namespace)) put(normalized, value)
    }
  }

  private fun MutableMap<String, String>.collectOptions(file: File, namespace: String) {
    // ignore missing files
    if (!file.exists()) return

    file.inputStream().use { Properties().apply { load(it) } }.forEach { (key, value) ->
      // filter by namespace and use type checks for convenience
      if (key is String && key.startsWith(namespace) && value is String) put(key, value)
    }
  }

  private companion object {
    private const val LOCAL_PROPERTIES = "local.properties"

    private const val GRADLE_PROPERTIES = "gradle.properties"

    private const val OPTIONS_EXTENSION = "options"
  }
}
