package io.github.darvld.buildkit.options

/** Shorthand for creating a new [Option] with the given [key] and using it to call [OptionsSource.resolve]. */
internal fun OptionsSource.resolve(key: String): String? {
  return resolve(Option.of(key))
}
