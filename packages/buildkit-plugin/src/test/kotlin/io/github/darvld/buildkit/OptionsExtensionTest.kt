package io.github.darvld.buildkit

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.io.File

class OptionsExtensionTest {
  /** Temporary project root. */
  @TempDir lateinit var projectDir: File

  @Test fun `use options in project plugin`() {
    // prepare project files
    projectDir.resolve("build.gradle.kts").writeText(
      """
      plugins {
          id("${TestConstants.PLUGIN_ID}") version "${TestConstants.PLUGIN_VERSION}"
      }
      
      // run assertions inside the build script
      check(options.orNull("env") == "envOption") { "expected env option to be present" }
      check(options.orNull("gradle") == "gradleOption") { "expected gradle option to be present" }
      check(options.orNull("overridden") == "replaced") { "expected local.properties to override option" }
      check(options.orNull("local") == "localOption") { "expected project-local option to be present" }
      """.trimIndent()
    )

    projectDir.resolve("settings.gradle.kts").writeText(
      """
      rootProject.name = "test"
      """.trimIndent()
    )

    projectDir.resolve("gradle.properties").writeText(
      """
      test.gradle=gradleOption
      test.overridden=original
      """.trimIndent()
    )

    projectDir.resolve("local.properties").writeText(
      """
      test.local=localOption
      test.overridden=replaced
      """.trimIndent()
    )

    // build project
    val project = GradleRunner.create()
      .withPluginClasspath()
      .withProjectDir(projectDir)
      .withEnvironment(mapOf("TEST_ENV" to "envOption"))
      .withArguments("tasks")

    assertDoesNotThrow("expected build to succeed") { project.build() }
  }
}
