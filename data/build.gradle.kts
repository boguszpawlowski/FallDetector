plugins {
    id(Kotlin.GradlePluginId)
    id("java-library")
}

dependencies {
    implementation(project(path = ":domain"))
}
