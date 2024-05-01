package io.github.darvld.buildkit.options

/** An [OptionsSource] backed by a [Map]. */
@JvmInline internal value class MapOptionsSource(private val values: Map<String, String>) : OptionsSource {
  override fun resolve(option: Option): String? {
    return values[option.key]
  }
}
