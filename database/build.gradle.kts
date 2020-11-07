plugins {
    id(Android.LibraryPluginId)
    kotlin(Kotlin.AndroidPluginId)
    kotlin(Kotlin.KaptPluginId)
}

kapt {
    correctErrorTypes = true
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

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
}

dependencies {
    coreLibraryDesugaring(Libraries.desugarJdkLibs)
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Libraries.room)
    implementation(Libraries.roomExtensions)
    kapt(Libraries.roomProcessor)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
    testImplementation(TestLibraries.mockito)
}
