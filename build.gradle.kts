plugins {
    id("java")
    id("com.gradleup.shadow") version "9.2.2"
}

group = "com.purityvanilla"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    shadow("io.papermc.paper", "paper-api", "1.21.1-R0.1-SNAPSHOT")
    implementation("org.spongepowered", "configurate-yaml", "4.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
