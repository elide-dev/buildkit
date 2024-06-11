package io.github.darvld.buildkit

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.io.File

class OptionsExtensionTest {
  /** Temporary project root. */
  @TempDir lateinit var projectDir: File

  @Test fun `should provide namespaced gradle properties`() {
    // prepare project files
    projectDir.resolve("build.gradle.kts").writeText(
      """
      plugins {
          id("${TestConstants.PLUGIN_ID}") version "${TestConstants.PLUGIN_VERSION}"
      }
      
      // run assertions inside the build script
      check(options.orNull("option") == "value") { "expected env option to be present" }
      """.trimIndent()
    )

    projectDir.resolve("settings.gradle.kts").writeText(
      """
      rootProject.name = "test"
      """.trimIndent()
    )

    projectDir.resolve("gradle.properties").writeText(
      """
      test.option=value
      """.trimIndent()
    )

    // build project
    val project = GradleRunner.create()
      .withProjectDir(projectDir)
      .withArguments("tasks")
      .withPluginClasspath()

    assertDoesNotThrow("expected build to succeed") { project.build() }
  }

  @Test fun `should use custom namespace`() {
    // prepare project files
    projectDir.resolve("build.gradle.kts").writeText(
      """
      plugins {
          id("${TestConstants.PLUGIN_ID}") version "${TestConstants.PLUGIN_VERSION}"
      }

      // run assertions inside the build script
      check(options.orNull("option") == "value") { "expected env option to be present" }
      """.trimIndent()
    )

    projectDir.resolve("settings.gradle.kts").writeText(
      """
      rootProject.name = "test"
      """.trimIndent()
    )

    projectDir.resolve("gradle.properties").writeText(
      """
      buildkit.namespace=custom
      custom.option=value
      """.trimIndent()
    )

    // build project
    val project = GradleRunner.create()
      .withProjectDir(projectDir)
      .withArguments("tasks")
      .withPluginClasspath()

    assertDoesNotThrow("expected build to succeed") { project.build() }
  }
}
