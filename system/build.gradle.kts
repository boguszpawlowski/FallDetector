plugins {
    id("com.android.library")
    kotlin(Kotlin.AndroidPluginId)
    kotlin(Kotlin.KaptPluginId)
    id(Kotlin.AndroidExtensionsPluginId)
}

android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    compileSdkVersion(App.Compile)

    defaultConfig {
        minSdkVersion(App.Min)
        targetSdkVersion(App.Min)
        versionCode = App.VersionCode
        versionName = App.VersionCode.toString()
    }
}

dependencies {
    coreLibraryDesugaring(Libraries.desugarJdkLibs)
    implementation(project(path = ":domain"))
    implementation(Libraries.location)
    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
}
