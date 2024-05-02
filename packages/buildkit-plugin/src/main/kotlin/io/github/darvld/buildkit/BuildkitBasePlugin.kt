package io.github.darvld.buildkit

import io.github.darvld.buildkit.options.EnvOptionsSource
import io.github.darvld.buildkit.options.LayeredOptionsSource
import io.github.darvld.buildkit.options.OptionsSource
import io.github.darvld.buildkit.options.PropertiesOptionSource
import org.gradle.api.Plugin
import java.nio.file.Path

/** Base class for plugins providing helpers to set up the [OptionsExtension] and other shared features. */
public abstract class BuildkitBasePlugin<T> : Plugin<T> {
  protected companion object {
    /** Name for the [OptionsExtension] added to a project. */
    internal const val OPTIONS_EXTENSION = "options"

    /** Name for a standard Gradle Properties file. */
    internal const val GRADLE_PROPERTIES_FILE = "gradle.properties"

    /** Name for a standard Gradle Local Properties file. */
    internal const val LOCAL_PROPERTIES_FILE = "local.properties"

    internal fun collectOptions(
      userHomePath: Path,
      projectPath: Path,
    ): OptionsSource {
      return LayeredOptionsSource(
        EnvOptionsSource,
        PropertiesOptionSource(projectPath.resolve(LOCAL_PROPERTIES_FILE)),
        PropertiesOptionSource(userHomePath.resolve(GRADLE_PROPERTIES_FILE)),
        PropertiesOptionSource(projectPath.resolve(GRADLE_PROPERTIES_FILE)),
      )
    }
  }
}
