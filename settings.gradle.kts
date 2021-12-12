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
    }
}

include(":core:api", ":core:domain", ":base:impl", ":base:persistence")

enableFeaturePreview("VERSION_CATALOGS")
include("persistence")
