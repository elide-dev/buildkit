package io.github.darvld.buildkit.options

/**
 * A retrievable option key, used with an [OptionsSource] to obtain a value. Keys are created using [Key.of], and must
 * comply with the following rules:
 *
 * - Keys are composed of one or more segments, separated by a dot (.), a dash (-), or a lower dash (_).
 * - Segments consist of one or more letters (a-z and A-Z) and/or digits (0-9).
 */
@JvmInline internal value class Option private constructor(val key: String) {
  internal companion object {
    /**
     * Regular expression to check validity of option keys. Matches groups of letters and numbers separated by a
     * `'.'`, `'-'`, or `'_'`.
     */
    private val KeyRegex = Regex("^[a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*$")

    /** Create a new [Option] with the given [key]. The key must comply with the following rules:
     *
     * - Keys are composed of one or more segments, separated by a dot (.), a dash (-), or a lower dash (_).
     * - Segments consist of one or more letters (a-z and A-Z) and/or digits (0-9).
     *
     * Invalid keys will throw an [IllegalArgumentException].
     */
    internal fun of(key: String): Option {
      require(KeyRegex.matches(key)) { "Option key does not match the expected regex: '${KeyRegex.pattern}'" }
      return Option(key)
    }
  }
}
