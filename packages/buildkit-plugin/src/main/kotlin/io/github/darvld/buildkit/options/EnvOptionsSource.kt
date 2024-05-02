package io.github.darvld.buildkit.options

/**
 * An [OptionsSource] backed by environment variables. Option keys are normalized before reading the env, using the
 * following rules:
 *
 * - All segment separators (`-`, `_`, and `.`) are replaced by `_`.
 * - The whole key is capitalized.
 *
 * For example, the key `buildkit.ci.upload` will be transformed into `BUILDKIT_CI_UPLOAD`.
 */
internal object EnvOptionsSource : OptionsSource {
  /** Separator used for the normalized form of option keys when checking for environment variables. */
  private const val ENV_SEPARATOR = "_"

  /** Separator characters allowed by option keys, to be replaced by the [ENV_SEPARATOR] character. */
  private val SeparatorRegex = Regex("[._-]")

  override fun resolve(option: Option): String? {
    val normalized = option.key.replace(SeparatorRegex, ENV_SEPARATOR).uppercase()
    return System.getenv(normalized)
  }
}
