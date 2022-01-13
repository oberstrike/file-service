plugins {
    kotlin("plugin.allopen") version "1.6.0"
    id("io.quarkus")
    id("org.kordamp.gradle.jandex")

}


val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

val testcontainersVersion = "1.16.2"


sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
    getByName("test").java.srcDirs("src/test/kotlin")
}


dependencies {
    implementation(project("::base::beans"))
    api(project("::core::api"))
    api(project("::core::domain"))



    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-vertx")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-container-image-docker")

    implementation("io.quarkus:quarkus-hibernate-validator")

    //Kotlin coroutines
    implementation(libs.kotlinCoroutines)

    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkiverse.mockk:quarkus-junit5-mockk:0.2.0")

}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")

}

