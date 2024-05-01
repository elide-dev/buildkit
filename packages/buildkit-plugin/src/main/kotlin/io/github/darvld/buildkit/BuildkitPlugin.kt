package io.github.darvld.buildkit

import io.github.darvld.buildkit.options.MapOptionsSource
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

public open class BuildkitPlugin : BuildkitBasePlugin<Project>() {
  override fun apply(target: Project) {
    // TODO(@darvld): use layered and prefixed sources
    val source = MapOptionsSource(emptyMap())

    // create the extension, inject the options source for the constructor
    target.extensions.create<OptionsExtension>(OPTIONS_EXTENSION, source)
  }
}
