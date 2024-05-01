package io.github.darvld.buildkit

import io.github.darvld.buildkit.options.MapOptionsSource
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create

public class BuildkitSettingsPlugin : BuildkitBasePlugin<Settings>() {
  override fun apply(target: Settings) {
    // TODO(@darvld): use layered and prefixed sources
    val source = MapOptionsSource(emptyMap())

    // create the extension, inject the options source for the constructor
    target.extensions.create<OptionsExtension>(OPTIONS_EXTENSION, source)
  }
}
