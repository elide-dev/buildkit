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
    // use the root name as prefix for options
    val namespace = project.rootProject.name

    val options = collectOptions(
      userHomePath = project.gradle.gradleUserHomeDir.toPath(),
      projectPath = project.layout.projectDirectory.asFile.toPath(),
    )

    // create the extension, inject the namespace and options source for the constructor
    return project.extensions.create<OptionsExtension>(OPTIONS_EXTENSION, namespace, options)
  }
}
