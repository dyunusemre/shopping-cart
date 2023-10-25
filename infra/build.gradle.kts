plugins {
    id("java")
    id("io.freefair.aspectj.post-compile-weaving") version "8.4"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

group = "org.example"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))

    implementation("org.aspectj:aspectjrt:1.9.19")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("ch.qos.logback:logback-classic:1.2.6")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.assertj:assertj-core:3.20.2")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<io.freefair.gradle.plugins.aspectj.AspectjCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}