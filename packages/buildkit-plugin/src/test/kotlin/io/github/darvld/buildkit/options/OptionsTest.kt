package io.github.darvld.buildkit.options

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class OptionsTest {
  @ParameterizedTest
  @ValueSource(
    strings = [
      "simple",
      "123numbers456",
      "multiple.segments",
      "aLtErNaTiNg.CaPs",
      "dash-segments",
      "Lower_Dash_Segments",
      "mixed.segment-separator_characters",
    ]
  )
  fun `should accept valid keys`(key: String) {
    assertDoesNotThrow("expected valid key to be accepted") {
      Option.of(key)
    }
  }

  @ParameterizedTest
  @ValueSource(
    strings = [
      "symbols!",
      "disallowed:separators",
      "empty..segments",
      "more__empty___segments",
      "even--more---empty----segments",
      ".leading-separator",
      "trailing-separator.",
      "multi\nline",
    ]
  )
  fun `should reject invalid keys`(key: String) {
    assertThrows<IllegalArgumentException>("expected invalid key to be rejected") {
      Option.of(key)
    }
  }
}
