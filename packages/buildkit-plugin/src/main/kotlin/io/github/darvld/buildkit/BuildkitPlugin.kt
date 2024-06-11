package io.github.darvld.buildkit

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

/** Build extensions improving usability and general enhancements. */
public open class BuildkitPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    // Propagate to subprojects
    target.subprojects { apply(this) }

    // Add the main extension
    registerOptionsExtension(target)
  }

  /** Configure and register the [OptionsExtension] from the standard set of option sources. */
  private fun registerOptionsExtension(project: Project): OptionsExtension {
    // create the extension
    return project.extensions.create<OptionsExtension>(OPTIONS_EXTENSION)
  }

  private companion object {
    /** Name for the [OptionsExtension] added to a project. */
    private const val OPTIONS_EXTENSION = "options"
  }
}
