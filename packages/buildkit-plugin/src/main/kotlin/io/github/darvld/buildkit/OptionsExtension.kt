package io.github.darvld.buildkit

import io.github.darvld.buildkit.options.Option
import io.github.darvld.buildkit.options.OptionsSource

/**
 * Options available to the build from multiple sources. The following sources are checked in descending order of
 * priority:
 *
 * - Environment variables.
 * - Project-level `local.properties`
 * - User-wide `gradle.properties`
 * - Project-level `gradle.properties`
 */
public abstract class OptionsExtension internal constructor(private val source: OptionsSource) {
  /** Resolve the value of an option with the given [name] from the [source]. */
  protected fun resolve(name: String): String? {
    return source.resolve(Option.of(name))
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
}
