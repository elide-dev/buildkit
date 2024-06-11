package io.github.darvld.buildkit

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

/** Build extensions improving usability and general enhancements. */
public open class BuildkitPlugin : BuildkitBasePlugin<Project>() {
  override fun apply(target: Project) {
    registerOptionsExtension(target)
  }

  /** Configure and register the [OptionsExtension] from the standard set of option sources. */
  private fun registerOptionsExtension(project: Project): OptionsExtension {
    // create the extension
    return project.extensions.create<OptionsExtension>(OPTIONS_EXTENSION)
  }
}
