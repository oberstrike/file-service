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

include(":api", ":domain", ":impl", ":persistence")

enableFeaturePreview("VERSION_CATALOGS")
include("persistence")
