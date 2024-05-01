package io.github.darvld.buildkit

public abstract class OptionsExtension(protected val namespace: String, protected val source: Map<String, String>) {
  /**
   * Resolve a project option by [name], throwing an exception if not found. The [name] will be automatically
   * prefixed with the project namespace (`<root project name>.<property>`).
   *
   * Set the `buildkit.prefix` property if you wish to use a namespace other than the root project name for options.
   */
  public operator fun invoke(name: String): String = source["$namespace.$name"] ?: error("Option $name is not set")

  /**
   * Resolve a project option by [name], returning a [default] value if not found. The [name] will be automatically
   * prefixed with the project namespace (`<root project name>.<property>`).
   *
   * Set the `buildkit.prefix` property if you wish to use a namespace other than the root project name for options.
   */
  public operator fun invoke(name: String, default: String): String = source["$namespace.$name"] ?: default

  /**
   * Resolve a project option by [name], returning null if not found. The [name] will be automatically
   * prefixed with the project namespace (`<root project name>.<property>`).
   *
   * Set the `buildkit.prefix` property if you wish to use a namespace other than the root project name for options.
   */
  public fun orNull(name: String): String? = source["$namespace.$name"]

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
