package io.github.darvld.buildkit.options

import io.github.darvld.buildkit.options.PropertiesOptionSource.LoadingMode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.writeText
import kotlin.test.assertEquals

class PropertiesOptionsTest {
  @TempDir lateinit var tempDir: Path

  @Test fun `should read from properties file`() {
    val props = tempDir.resolve("test.properties")

    // language=Properties
    props.writeText(
      """
      test.option.property=test-value
      """.trimIndent()
    )

    val source = PropertiesOptionSource(file = props)
    assertEquals(
      expected = "test-value",
      actual = source.resolve("test.option.property")
    )
  }

  @Test fun `should defer read until first request`() {
    val props = tempDir.resolve("test.properties")

    // language=Properties
    props.writeText(
      """
      test.option.property=initial
      """.trimIndent()
    )

    val source = PropertiesOptionSource(file = props, mode = LoadingMode.LAZY)

    // language=Properties
    props.writeText(
      """
      test.option.property=updated
      """.trimIndent()
    )

    assertEquals(
      expected = "updated",
      actual = source.resolve("test.option.property")
    )
  }

  @Test fun `should read on construction`() {
    val props = tempDir.resolve("test.properties")

    // language=Properties
    props.writeText(
      """
      test.option.property=initial
      """.trimIndent()
    )

    val source = PropertiesOptionSource(file = props, mode = LoadingMode.EAGER)

    // language=Properties
    props.writeText(
      """
      test.option.property=updated
      """.trimIndent()
    )

    assertEquals(
      expected = "initial",
      actual = source.resolve("test.option.property")
    )
  }
}
