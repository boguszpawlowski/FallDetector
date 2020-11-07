plugins {
    kotlin(Kotlin.GradleJvmPluginId)
}

dependencies {
    api(Kotlin.StdLib)
    api(Libraries.timber)
    api(Libraries.koin)
    api(Libraries.koinAndroid)
    api(Libraries.coroutines)
}
