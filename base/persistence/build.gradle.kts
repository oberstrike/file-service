plugins {
    kotlin("plugin.allopen") version "1.6.0"
    id("io.quarkus")
    id("org.jetbrains.kotlin.plugin.noarg") version "1.6.0"
    id("org.kordamp.gradle.jandex")
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
    getByName("test").java.srcDirs("src/test/kotlin")
}

val mockkVersion = "1.12.1"
val testcontainersVersion = "1.16.2"
val jnanoid = "2.0.0"

dependencies{
    api(project("::core::api"))
    api(project("::core::domain"))
    api(project("::core::impl"))
    implementation("org.jetbrains.kotlin:kotlin-noarg:1.6.0")

    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache")
    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("com.aventrix.jnanoid:jnanoid:$jnanoid")

    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")




    // https://mvnrepository.com/artifact/io.smallrye.reactive/mutiny-kotlin
    implementation("io.smallrye.reactive:mutiny-kotlin:1.2.0")

    implementation(libs.kotlinCoroutines)
    testImplementation(libs.kotlinCoroutinesTest)

    testImplementation(libs.junit5Jupiter)
    testImplementation(libs.junit5JupiterApi)
    testImplementation(libs.junit5JupiterParams)
    testRuntimeOnly(libs.junit5JupiterEngine)
    testImplementation(libs.kluent)
    testImplementation(libs.mockk)

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
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")

}

configure<org.jetbrains.kotlin.noarg.gradle.NoArgExtension> {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")

}
