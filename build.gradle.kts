import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.buildTimeTracker)
}

subprojects {
  // configure projects after they are evaluated, this allows for certain extensions and plugins
  // like Java and Kotlin to be present at the time when our custom logic runs
  afterEvaluate {
    // publish everything by default, but allow projects to opt out
    if (findProperty("buildkit.release") == "true" && findProperty("buildkit.publish") == "true") {
      configurePublishing(this)
    }

    // configure JVM compatibility for all projects
    val targetJvm = property("buildkit.jvm-target").toString()

    extensions.findByType<JavaPluginExtension>()?.apply {
      sourceCompatibility = JavaVersion.valueOf("VERSION_$targetJvm")
      targetCompatibility = JavaVersion.valueOf("VERSION_$targetJvm")
    }

    tasks.withType<JavaCompile> {
      sourceCompatibility = targetJvm
      targetCompatibility = targetJvm
    }

    tasks.withType<KotlinCompile> {
      compilerOptions.jvmTarget = JvmTarget.valueOf("JVM_$targetJvm")
    }
  }
}

fun configurePublishing(project: Project): Unit = with(project) {
  // skip empty projects; only those with the Java extension are eligible for publishing
  val javaExtension = extensions.findByType<JavaPluginExtension>() ?: return@with

  // if (property("buildkit.sign") == "true") apply(plugin = "signing")
  apply(plugin = "maven-publish")

  val publishing = extensions.getByType<PublishingExtension>()
  //  val signing = extensions.findByType<SigningExtension>()

  // signing configuration (newlines in the key must be un-escaped)
  //  signing?.useInMemoryPgpKeys(
  //    /* defaultKeyId = */ option("signing.key-id"),
  //    /* defaultSecretKey = */ option("signing.key").trim('"').replace("\\n", "\n"),
  //    /* defaultPassword = */ option("signing.password")
  //  )

  // package sources and javadocs into publishable artifacts
  javaExtension.apply {
    withSourcesJar()
    withJavadocJar()
  }

  // create a publication for the project, allow opt-out (e.g. Gradle plugins create their own)
  if (findProperty("buildkit.publishing.implicit") != "false") {
    val artifact = findProperty("artifact")?.toString() ?: project.name
    
    // convert snake case to lowerCamelCase
    val publicationName = artifact.lowercase().replace(Regex("-(.)")) {
      it.groupValues[1].uppercase()
    }

    publishing.publications.create<MavenPublication>(publicationName) {
      // use the project name as default artifact id, allow overrides
      artifactId = artifact

      // publish all JVM artifacts
      from(components["java"])
    }
  }

  // configure all publications
  publishing.publications.withType<MavenPublication>().configureEach {
    // sign all artifacts in this publication
    //    signing?.sign(this)

    // add metadata
    pom {
      name = project.name
      description = project.description
      url = "https://github.com/darvld/buildkit"

      licenses {
        license {
          name = "Apache License 2.0"
          url = "https://www.apache.org/licenses/LICENSE-2.0"
        }
      }

      developers {
        developer {
          id = "darvld"
          name = "Dario Valdespino"
          email = "dvaldespino00@gmail.com"
        }
      }

      scm {
        connection = "scm:git:ssh://github.com/darvld/buildkit.git"
        url = "https://github.com/darvld/buildkit/tree/main"
      }
    }
  }
}
