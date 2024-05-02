package io.github.darvld.buildkit

import io.github.darvld.buildkit.options.EnvOptionsSource
import io.github.darvld.buildkit.options.LayeredOptionsSource
import io.github.darvld.buildkit.options.ProjectOptionsSource
import io.github.darvld.buildkit.options.PropertiesOptionSource
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
    val options = LayeredOptionsSource(
      EnvOptionsSource,
      // local.properties
      PropertiesOptionSource(project.layout.projectDirectory.file(LOCAL_PROPERTIES_FILE).asFile.toPath()),
      // gradle-provided props (CLI, gradle.properties, user properties)
      ProjectOptionsSource(project),
    )

    // create the extension, inject the namespace and options source for the constructor
    return project.extensions.create<OptionsExtension>(OPTIONS_EXTENSION, namespace, options)
  }
}
