plugins {
    id("java")
}

group = "ru.esqlapy"
version = "1.6.2"

repositories {
    mavenCentral()
    maven(url = "https://maven.lavalink.dev/releases")
}

dependencies {
    implementation("net.dv8tion:JDA:5.1.1")
    implementation("ch.qos.logback:logback-classic:1.5.8")
    implementation("dev.arbjerg:lavaplayer:2.2.2")
    implementation("dev.lavalink.youtube:v2:1.8.3")
    // need only because dev.arbjerg:lavaplayer:2.2.1 implement commons-codec:commons-codec:1.11 with
    // vulnerability Cxeb68d52e-5509
    implementation("commons-codec:commons-codec:1.17.1")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ru.esqlapy.Main"
        attributes["Implementation-Version"] = version
        archiveFileName = "vega.jar"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}
