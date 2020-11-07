plugins {
    id(Android.ApplicationPluginId)
    kotlin(Kotlin.AndroidPluginId)
    kotlin(Kotlin.KaptPluginId)
    id(Android.SafeArgsPluginId)
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

    defaultConfig {
        compileSdkVersion(App.Compile)
        defaultConfig {
            minSdkVersion(App.Min)
            targetSdkVersion(App.Min)
            versionCode = App.VersionCode
            versionName = App.VersionCode.toString()
        }
    }

    buildTypes {
        named("debug") {}
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerVersion = Kotlin.Version
        kotlinCompilerExtensionVersion =Compose.Version
    }
}

dependencies {
    coreLibraryDesugaring(Libraries.desugarJdkLibs)

    implementation(project(path = ":system"))
    implementation(project(path = ":domain"))
    implementation(project(path = ":data"))
    implementation(project(path = ":database"))

    implementation(AndroidX.coreKtx)
    implementation(Libraries.material)
    implementation(Libraries.appCompat)
    implementation(Libraries.fragment)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.preference)
    implementation(Libraries.lifecycle)
    implementation(Libraries.lifecycleViewModel)
    implementation(Libraries.navigation)
    implementation(Libraries.navigationExtensions)
    implementation(Libraries.cameraXCore)
    implementation(Libraries.camera2)
    implementation(Libraries.cameraView)
    implementation(Libraries.cameraXExtensions)
    implementation(Libraries.epoxy)
    implementation(Libraries.dexter)
    implementation(Libraries.stetho)
    implementation(Libraries.glide)
    implementation(Libraries.dialogs)
    implementation(Libraries.dialogsBottomSheets)

    implementation(Compose.Ui)
    implementation(Compose.Runtime)
    implementation(Compose.Foundation)
    implementation(Compose.FoundationLayout)
    implementation(Compose.Material)
    implementation(Compose.MaterialIconsExtended)
    implementation(Compose.UiTooling)
    implementation(Compose.CoilImage)

    implementation(Libraries.koinViewModel)

    kapt(Libraries.epoxyProcessor)
    kapt(Libraries.glideProcessor)

    debugImplementation(Debug.LeakCanary)
    debugImplementation(Debug.RedScreenOfDeath)
    debugImplementation(Hyperion.Core)
    debugImplementation(Hyperion.Timber)
    debugImplementation(Hyperion.SharedPrefs)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
}
