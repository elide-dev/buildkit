package io.github.darvld.buildkit.options

import org.gradle.api.Project

@JvmInline internal value class ProjectOptionsSource(private val project: Project) : OptionsSource {
  override fun resolve(option: Option): String? {
    return project.findProperty(option.key) as? String
  }
}
