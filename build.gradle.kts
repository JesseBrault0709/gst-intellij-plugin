plugins {
    id("gst.intellij.common")
    id("java")
    id("org.jetbrains.intellij").version("1.13.3")
}

group = "com.jessebrault"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.1.2")
    type.set("IC")
    plugins.add("org.intellij.groovy")
}

dependencies {
    implementation("com.jessebrault.gst:lib") {
        exclude("org.slf4j", "slf4j-api")
    }

    // https://mvnrepository.com/artifact/junit/junit
    testImplementation("junit:junit:4.13.2")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")

    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.8.2")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    // https://mvnrepository.com/artifact/org.junit.vintage/junit-vintage-engine
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.3")
}

tasks {
    withType<JavaCompile> {
        targetCompatibility = "17"
        sourceCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("231")
        version.set(project.version.toString())
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    test {
        useJUnitPlatform()
    }
}