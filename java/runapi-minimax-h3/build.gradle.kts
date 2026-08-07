plugins {
  `java-library`
  `maven-publish`
}

extra["runapiSlug"] = "minimax-h3"

description = "RunAPI MiniMax H3 Java SDK for MiniMax H3 workflows."

java {
  withSourcesJar()
  withJavadocJar()
}

dependencies {
  api("ai.runapi:runapi-core:0.3.0")

  testImplementation(platform("org.junit:junit-bom:5.10.3"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifactId = "runapi-minimax-h3"
      pom {
        name = "RunAPI MiniMax H3 Java SDK"
        description = "RunAPI MiniMax H3 Java SDK for MiniMax H3 workflows."
        url = "https://runapi.ai/models/minimax-h3"
        licenses {
          license {
            name = "Apache License, Version 2.0"
            url = "https://www.apache.org/licenses/LICENSE-2.0"
          }
        }
        developers {
          developer {
            id = "runapi"
            name = "RunAPI"
            email = "contact@runapi.ai"
          }
        }
        scm {
          url = "https://github.com/runapi-ai/minimax-h3-sdk"
          connection = "scm:git:https://github.com/runapi-ai/minimax-h3-sdk.git"
          developerConnection = "scm:git:ssh://git@github.com/runapi-ai/minimax-h3-sdk.git"
        }
      }
    }
  }
}
