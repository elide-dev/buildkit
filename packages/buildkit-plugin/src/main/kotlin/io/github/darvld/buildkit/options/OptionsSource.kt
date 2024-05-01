package io.github.darvld.buildkit.options

/**
 * A resolver for arbitrary option keys.
 *
 * Implementations may transform the option key during resolution to match the underlying source, e.g. capitalization
 * rules for environment variables or prefixes for gradle properties.
 */
internal fun interface OptionsSource {
  /**
   * Attempt to resolve a value for the given [option], returning `null` if not available.
   *
   * Implementations should avoid returning a default value to allow higher-level components to set such behavior
   * instead. Caching the fetched value for the option is generally allowed in cases where they are unlikely to be
   * updated.
   */
  fun resolve(option: Option): String?
}
