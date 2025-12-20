import org.gradle.internal.os.OperatingSystem

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("org.graalvm.buildtools.native") version "0.11.3"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation(platform("org.junit:junit-bom:5.14.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "org.example.Main"
}

tasks.test {
    useJUnitPlatform()
}

graalvmNative {
    binaries {
        named("main")  {
            imageName.set("pkce-code-challenge-generator")
            mainClass.set("org.example.Main")

            val os = OperatingSystem.current()
            if (os.isLinux) {
                buildArgs.add("--static")
                buildArgs.add("--libc=musl")
            } else if (os.isMacOsX) {
                // Do not use static linking on macOS.
            } else if (os.isWindows) {
                // This project does not support building native images on Windows at this time.
            }
        }
    }
}
