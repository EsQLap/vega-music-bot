plugins {
    id("java")
}

group = "ru.esqlapy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://maven.lavalink.dev/releases")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.1")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("dev.arbjerg:lavaplayer:2.2.1")
    implementation("dev.lavalink.youtube:v2:1.5.0")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}