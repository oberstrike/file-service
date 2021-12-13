rootProject.name = "file-service"

pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
        id("org.kordamp.gradle.jandex") version "0.11.0"
    }
}

include(":base:web", ":core:api", ":core:domain", ":core:impl", ":base:persistence")

enableFeaturePreview("VERSION_CATALOGS")
include(":base:beans")
