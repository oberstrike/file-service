rootProject.name = "file-service"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":api", ":domain", ":impl")

enableFeaturePreview("VERSION_CATALOGS")
