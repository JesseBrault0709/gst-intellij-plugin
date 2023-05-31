rootProject.name = "gst-intellij-plugin"

pluginManagement {
    repositories {
        gradlePluginPortal()

        maven {
            url = uri("https://archiva.jessebrault.com/repository/internal/")

            credentials {
                username = System.getenv("JBARCHIVA_USERNAME")
                password = System.getenv("JBARCHIVA_PASSWORD")
            }
        }

        maven {
            url = uri("https://archiva.jessebrault.com/repository/snapshots/")

            credentials {
                username = System.getenv("JBARCHIVA_USERNAME")
                password = System.getenv("JBARCHIVA_PASSWORD")
            }
        }
    }
}

includeBuild("gst") {
    dependencySubstitution {
        substitute(module("com.jessebrault.gst:lib")).using(project(":lib"))
    }
}