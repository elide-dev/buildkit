package io.github.darvld.buildkit.options

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LayeredOptionsTest {
  @Test fun `should use layers as fallback`() {
    val mainOptions = mapOf("ci" to "true")
    val fallbackOptions = mapOf("ci" to "false", "debug" to "true")

    val source = LayeredOptionsSource(
      MapOptionsSource(mainOptions),
      MapOptionsSource(fallbackOptions),
    )

    assertEquals("true", source.resolve("ci"), "should override fallback value")
    assertEquals("true", source.resolve("debug"), "should use fallback value if not found")
    assertNull(source.resolve("invalid"), "should return null for non-existent option")
  }
}
