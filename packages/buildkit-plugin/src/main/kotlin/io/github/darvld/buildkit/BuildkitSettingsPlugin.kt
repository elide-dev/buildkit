package io.github.darvld.buildkit

import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create

/** Build extensions improving usability and general enhancements. */
public class BuildkitSettingsPlugin : BuildkitBasePlugin<Settings>() {
  override fun apply(target: Settings) {
    registerOptionsExtension(target)
  }

  /** Configure and register the [OptionsExtension] from the standard set of option sources. */
  @Suppress("UnstableApiUsage")
  private fun registerOptionsExtension(settings: Settings): OptionsExtension {
    // use the root name as prefix for options
    val namespace = settings.rootProject.name

    val options = collectOptions(
      userHomePath = settings.gradle.gradleUserHomeDir.toPath(),
      projectPath = settings.layout.rootDirectory.asFile.toPath(),
    )

    // create the extension, inject the namespace and options source for the constructor
    return settings.extensions.create<OptionsExtension>(OPTIONS_EXTENSION, namespace, options)
  }
}
