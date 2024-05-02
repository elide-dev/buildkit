package io.github.darvld.buildkit.options

import io.github.darvld.buildkit.options.PropertiesOptionSource.LoadingMode
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.inputStream

/**
 * An [OptionsSource] backed by [Properties]. The property values are read only once, either immediately on
 * construction, or during the first [resolve] call, depending on the [LoadingMode].
 */
internal class PropertiesOptionSource(file: Path, mode: LoadingMode = LoadingMode.EAGER) : OptionsSource {
  /** Determines the time at which a Properties source file is read. */
  internal enum class LoadingMode {
    /** Read the properties source immediately and cache the result. */
    EAGER,

    /** Read the properties source during the first [resolve] call and cache the result. */
    LAZY,
  }

  /** Cached properties map. */
  private val properties by readSource(file, mode)

  /**
   * Read a [source] file and return either a pre-initialized [Lazy] delegate (if [LoadingMode.EAGER] is selected),
   * or a deferred delegate if [LoadingMode.LAZY] is used.
   */
  private fun readSource(source: Path, mode: LoadingMode): Lazy<Properties> = when (mode) {
    LoadingMode.EAGER -> lazyOf(readProperties(source))
    LoadingMode.LAZY -> lazy { readProperties(source) }
  }

  /** Read and parse the given [source] as a [Properties] map. */
  private fun readProperties(source: Path): Properties {
    val props = Properties()

    // safely ignore nonexistent files, return an empty map instead
    if (source.exists()) source.inputStream().use(props::load)

    return props
  }

  override fun resolve(option: Option): String? {
    return properties.getProperty(option.key)
  }
}
