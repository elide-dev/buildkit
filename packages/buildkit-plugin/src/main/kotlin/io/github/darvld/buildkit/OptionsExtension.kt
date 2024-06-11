package io.github.darvld.buildkit

import org.gradle.api.Project

/**
 * Namespaced options drawn from Project properties, using the format `<namespace>.<option>`. The namespace is set to
 * the root project name by default, but can be set using the `buildkit.namespace` property.
 */
public abstract class OptionsExtension(private val project: Project) {
  /** The namespace used for resolving options in this project */
  private val namespace: String by lazy {
    project.findProperty(NAMESPACE_PROPERTY) as? String ?: project.rootProject.name
  }

  /** Resolve the value of an option with the given [name] from the [project]. */
  private fun resolve(name: String): String? {
    val value = project.findProperty("$namespace.$name") as? String
    return value?.trim()
  }

  /**
   * Resolve a project option by [name], throwing an exception if not found. The [name] will be automatically
   * prefixed with the project namespace (`<root project name>.<property>`).
   *
   * Set the `buildkit.prefix` property if you wish to use a namespace other than the root project name for options.
   */
  public operator fun invoke(name: String): String = resolve(name) ?: error("Option $name is not set")

  /**
   * Resolve a project option by [name], returning a [default] value if not found. The [name] will be automatically
   * prefixed with the project namespace (`<root project name>.<property>`).
   *
   * Set the `buildkit.prefix` property if you wish to use a namespace other than the root project name for options.
   */
  public operator fun invoke(name: String, default: String): String = resolve(name) ?: default

  /**
   * Resolve a project option by [name], returning null if not found. The [name] will be automatically
   * prefixed with the project namespace (`<root project name>.<property>`).
   *
   * Set the `buildkit.prefix` property if you wish to use a namespace other than the root project name for options.
   */
  public fun orNull(name: String): String? = resolve(name)

  /**
   * Resolve a project option by [name] and parse it as a boolean, or returning a [default] value if not found. The
   * [name] will be automatically prefixed with the project namespace (`<root project name>.<property>`).
   *
   * * Set the `buildkit.prefix` property if you wish to use a namespace other than the root project name for options.
   */
  public fun enabled(name: String, default: Boolean = false): Boolean {
    return orNull(name)?.toBooleanStrictOrNull() ?: default
  }

  private companion object {
    /** Name of the Gradle property used to override the options namespace. */
    private const val NAMESPACE_PROPERTY = "buildkit.namespace"
  }
}
