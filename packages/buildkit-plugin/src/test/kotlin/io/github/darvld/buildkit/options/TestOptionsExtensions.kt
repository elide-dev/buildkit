package io.github.darvld.buildkit.options

internal fun OptionsSource.resolve(key: String): String? {
  return resolve(Option.of(key))
}
