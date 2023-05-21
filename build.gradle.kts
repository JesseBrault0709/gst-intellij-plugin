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
}

dependencies {
    implementation("com.jessebrault.gst:lib")
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
}