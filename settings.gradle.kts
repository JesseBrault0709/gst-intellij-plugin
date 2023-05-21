rootProject.name = "gst-intellij-plugin"

includeBuild("gst") {
    dependencySubstitution {
        substitute(module("com.jessebrault.gst:lib")).using(project(":lib"))
    }
}