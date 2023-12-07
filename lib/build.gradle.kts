plugins {
    kotlin("jvm") version "1.9.10"
    `java-library`
    `maven-publish`
}

group = "com.paul-samuels"
version = "0.1.0"

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
        create<MavenPublication>("maestro.dsl") {
            artifactId = "maestro-dsl"
            from(components["java"])
        }
    }
}

kotlin {
    jvmToolchain(8)
    explicitApi()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
