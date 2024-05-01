package io.github.darvld.buildkit

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class OptionsExtensionTest {
  @TempDir lateinit var projectDir: File

  @Test fun `should include gradle properties`() {
    testBuild {
      buildScript(
        """
        plugins {
          id("io.github.darvld.buildkit")
        }
        
        check(options("direct") == "direct value") { "expected direct option to be present" }
        check(options("local") == "local value") { "expected project-local option to be present" }
        check(options("env") == "env value") { "expected env option to be present" }
        """.trimIndent()
      )

      settingsScript("rootProject.name = \"test\"")
      gradleProperties("test.direct=direct value")
      localProperties("test.local=local value")
      environment("TEST_ENV" to "env value")
    }
  }
}
