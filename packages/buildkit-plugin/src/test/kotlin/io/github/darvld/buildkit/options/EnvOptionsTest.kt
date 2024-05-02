package io.github.darvld.buildkit.options

import io.github.darvld.buildkit.TestConstants
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class EnvOptionsTest {
  @ParameterizedTest
  @ValueSource(
    strings = [
      "test.env",
      "test-env",
      "test_env",
      "TEST.ENV",
      "TEST-ENV",
      "TEST_ENV",
    ]
  )
  fun `should resolve option from env`(key: String) {
    assertEquals(
      expected = TestConstants.TEST_ENV_VALUE,
      actual = EnvOptionsSource.resolve(key)
    )
  }
}
