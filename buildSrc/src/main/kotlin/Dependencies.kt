object Kotlin {
    const val Version = "1.4.10"

    const val GradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$Version"
    const val AndroidPluginId = "kotlin-android"
    const val AndroidExtensionsPluginId = "kotlin-android-extensions"

    const val StdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$Version"
    const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$Version"
}

object Android {
    const val GradlePlugin = "com.android.tools.build:gradle:4.2.0-alpha13"
    const val ApplicationPluginId = "com.android.application"
    const val SafeArgsPlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0"
    const val DetektCli = "io.gitlab.arturbosch.detekt:detekt-cli:1.12.0"
}

object App {
    const val VersionCode = 1
    const val Id = "com.bpawlowski.FallDetector"
    const val Min = 24
    const val Compile = 30
    const val Target = Compile
}

object Libraries {
    private object Versions {
        const val coroutines = "1.4.1"

        const val androidxAppCompat = "1.2.0-rc01"
        const val lifecycle = "2.2.0"
        const val preference = "1.1.1"
        const val cameraX = "1.0.0-alpha05"
        const val cameraXExt = "1.0.0-alpha02"
        const val navigationComponent = "2.3.0"
        const val room = "2.3.0-alpha02"

        const val recycler = "1.2.0-alpha01"
        const val material = "1.3.0-alpha02"
        const val constraint = "2.0.1"

        const val retrofit = "2.9.0"
        const val okHttp = "4.8.1"
        const val stetho = "1.5.1"
        const val timber = "4.7.1"
        const val koin = "2.1.0"
        const val glide = "4.11.0"
        const val dexter = "6.2.1"
        const val dialogs = "3.1.0"
        const val gson = "2.8.6"
        const val gsonConverter = "2.9.0"
        const val maps = "17.0.0"
        const val mapUtils = "2.0.3"
        const val epoxy = "4.1.0"
        const val desugaring = "1.0.9"
    }

    const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:${Versions.desugaring}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    const val preference = "androidx.preference:preference:${Versions.preference}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val cameraXCore = "androidx.camera:camera-core:${Versions.cameraX}"
    const val camera2 = "androidx.camera:camera-camera2:${Versions.cameraX}"
    const val cameraView = "androidx.camera:camera-view:${Versions.cameraXExt}"
    const val cameraXExtensions = "androidx.camera:camera-extensions:${Versions.cameraXExt}"
    const val navigation =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponent}"
    const val navigationExtensions =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponent}"

    const val recycler = "androidx.recyclerview:recyclerview:${Versions.recycler}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"

    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
    const val dexter = "com.karumi:dexter:${Versions.dexter}"
    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val dialogs = "com.afollestad.material-dialogs:core:${Versions.dialogs}"
    const val dialogsBottomSheets =
        "com.afollestad.material-dialogs:bottomsheets:${Versions.dialogs}"
    const val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"
    const val mapUtils = "com.google.maps.android:android-maps-utils:${Versions.mapUtils}"
    const val location = "com.google.android.gms:play-services-location:${Versions.maps}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomProcessor = "androidx.room:room-compiler:${Versions.room}"
    const val roomExtensions = "androidx.room:room-ktx:${Versions.room}"

    const val koin = "org.koin:koin-core:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
}

object AndroidX {
    const val Version = "1.5.0-alpha02"

    const val core = "androidx.core:core:$Version"
    const val coreKtx = "androidx.core:core-ktx:$Version"
    const val cardView = "androidx.cardview:cardview:$Version"
}

object Compose {
	const val Version = "1.0.0-alpha05"
	const val AccompanistVersion = "0.3.1"

    const val Runtime = "androidx.compose.runtime:runtime:$Version"
    const val Foundation = "androidx.compose.foundation:foundation:$Version"
    const val FoundationLayout = "androidx.compose.foundation:foundation-layout:$Version"
    const val Material = "androidx.compose.material:material:$Version"
    const val Ui = "androidx.compose.ui:ui:$Version"
    const val UiTooling = "androidx.ui:ui-tooling:$Version"
    const val MaterialIconsExtended = "androidx.compose.material:material-icons-extended:$Version"
    const val CoilImage = "dev.chrisbanes.accompanist:accompanist-coil:$AccompanistVersion"
    const val ViewBinding = "androidx.compose.ui:ui-viewbinding:$Version"

    const val Navigation = "androidx.navigation:navigation-compose:1.0.0-alpha01"
}

object Debug {
    const val LeakCanary = "com.squareup.leakcanary:leakcanary-android:2.4"
    const val RedScreenOfDeath = "com.melegy.redscreenofdeath:red-screen-of-death:0.1.1"
}

object Hyperion {
    const val Version = "0.9.30"

    const val Core = "com.willowtreeapps.hyperion:hyperion-core:$Version"
    const val Timber = "com.willowtreeapps.hyperion:hyperion-timber:$Version"
    const val SharedPrefs = "com.willowtreeapps.hyperion:hyperion-shared-preferences:$Version"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13"
        const val testRunner = "1.3.0"
        const val espresso = "3.3.0"
        const val mockito = "2.2.0" //todo replace with mockk
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito}"
    const val InstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
