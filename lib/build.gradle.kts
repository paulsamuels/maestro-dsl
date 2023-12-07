plugins {
    kotlin("jvm") version "1.9.10"
    `java-library`
    `maven-publish`
    `signing`
}

group = "com.paul-samuels"
version = "0.0.1"

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.2")
}

publishing {
    publications {
        create<MavenPublication>("maestro-dsl") {
            artifactId = "maestro-dsl"
            from(components["java"])

            pom {
                name = "Maestro Dsl"
                description = "A Kotlin Dsl for building Maestro scripts"
                url = "https://github.com/paulsamuels/maestro-dsl"

                developers {
                    developer {
                        id = "paulsamuels"
                        name = "Paul Samuels"
                        email = "paulio1987@gmail.com"
                    }
                }

                scm {
                    url = "https://github.com/paulsamuels/maestro-dsl"
                }

                licenses {
                    license {
                        name = "MIT"
                    }
                }
            }
        }
    }

    repositories {
        maven {
            url = uri(
                if (version.toString().endsWith("SNAPSHOT")) {
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                } else {
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                },
            )
            credentials {
                val ossrhUsername: String by properties
                val ossrhPassword: String by properties

                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
}

signing {
    sign(publishing.publications)
}

kotlin {
    jvmToolchain(8)
    explicitApi()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
