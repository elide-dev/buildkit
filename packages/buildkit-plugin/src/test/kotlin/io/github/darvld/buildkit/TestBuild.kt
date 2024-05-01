package io.github.darvld.buildkit

import org.intellij.lang.annotations.Language
import java.nio.file.Path

public interface TestBuild {

  public fun buildScript(@Language("kotlin") code: String)

  public fun settingsScript(@Language("kotlin") code: String)

  public fun gradleProperties(@Language("Properties") code: String)

  public fun localProperties(@Language("Properties") code: String)

  public fun subproject(name: String): TestBuild
  
  public fun resolve(path: Path): Path
  
  public fun environment(vararg envs: Pair<String, String>)
}

public fun testBuild(setup: TestBuild.() -> Unit) {
  
}
