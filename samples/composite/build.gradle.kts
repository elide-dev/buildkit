plugins {
  id("io.github.darvld.buildkit")
}


subprojects {
  tasks.register("useOptions") {
    doLast { println("${this@subprojects.name}: ${options("message", "default")}") }
  }
}
