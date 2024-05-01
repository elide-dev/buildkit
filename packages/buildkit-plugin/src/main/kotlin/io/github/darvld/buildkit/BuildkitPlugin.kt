package io.github.darvld.buildkit

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.io.File
import java.util.*

public open class BuildkitPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    val namespace = target.rootProject.name
    val options = target.collectOptions(namespace)
    
    target.extensions.create<OptionsExtension>(OPTIONS_EXTENSION, namespace, options)
  }

  private fun Project.collectOptions(namespace: String): Map<String, String> = buildMap {
    // basic project properties (lowest priority)
    properties.forEach { (key, value) ->
      if (key.startsWith(namespace)) put(key, value as String)
    }

    // from local.properties in the root project directory (overrides project properties)
    collectOptions(rootProject.layout.projectDirectory.file(LOCAL_PROPERTIES).asFile, namespace)

    // from local.properties in the project directory (overrides root project local properties)
    collectOptions(layout.projectDirectory.file(LOCAL_PROPERTIES).asFile, namespace)

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
