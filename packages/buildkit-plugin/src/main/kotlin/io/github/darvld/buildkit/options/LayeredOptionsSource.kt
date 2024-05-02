package io.github.darvld.buildkit.options

/**
 * A layered [OptionsSource] which falls back to each subsequent layer during resolution. Sources are queried until a
 * non-null value is returned or until all layers are exhausted.
 */
@JvmInline internal value class LayeredOptionsSource private constructor(
  private val layers: Iterable<OptionsSource>
) : OptionsSource {
  /**
   * Constructs a new [LayeredOptionsSource] with the given [layers], in descending order of priority, i.e. the first
   * source will be checked first before falling back to the next.
   */
  internal constructor(vararg layers: OptionsSource) : this(layers.toList())

  override fun resolve(option: Option): String? {
    return layers.firstNotNullOfOrNull { it.resolve(option).also { println("Resolving $option, found=$it") } }
  }
}
