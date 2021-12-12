tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to "de.ma.filedata")
    }
}

sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
    getByName("test").java.srcDirs("src/test/kotlin")
}


dependencies {
    api(project("::core::domain"))
    api(project("::core::api"))

    //Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC2")


    testImplementation(libs.junit5Jupiter)
    testImplementation(libs.junit5JupiterApi)
    testImplementation(libs.junit5JupiterParams)
    testRuntimeOnly(libs.junit5JupiterEngine)
    testImplementation(libs.kluent)
    testImplementation(libs.mockk)


    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")

}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
