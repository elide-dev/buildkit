package io.github.darvld.buildkit

import org.gradle.api.Plugin

/** Base class for plugins providing helpers to set up the [OptionsExtension] and other shared features. */
public abstract class BuildkitBasePlugin<T> : Plugin<T> {
  protected companion object {
    /** Name for the [OptionsExtension] added to a project. */
    internal const val OPTIONS_EXTENSION = "options"

    /** Name for a standard Gradle Properties file. */
    internal const val GRADLE_PROPERTIES_FILE = "gradle.properties"

    /** Name for a standard Gradle Local Properties file. */
    internal const val LOCAL_PROPERTIES_FILE = "local.properties"
  }
}
