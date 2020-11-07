import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(DetektDependencies.GradlePluginId) version DetektDependencies.Version
    id(GradleVersions.GradlePluginId) version GradleVersions.Version
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
        jcenter()
    }

    dependencies {
        classpath(Android.GradlePlugin)
        classpath(Kotlin.GradlePlugin)
        classpath(Android.SafeArgsPlugin)
        classpath(DetektDependencies.GradlePlugin)
        classpath(kotlin("gradle-plugin", Kotlin.Version))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }

    tasks.withType<Test> {
        testLogging {
            events("skipped", "failed", "passed")
        }
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                "-progressive",
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.Experimental",
                "-Xallow-jvm-ir-dependencies"
            )
        }
    }

//    plugins.withType<BasePlugin> {
//        extension.compileOptions {
//            sourceCompatibility = JavaVersion.VERSION_1_8
//            targetCompatibility = JavaVersion.VERSION_1_8
//        }
//
//        extensions.findByType<BaseExtension>()?.apply {
//            compileSdkVersion(Build.CompileSdk)
//            buildToolsVersion(Build.BuildToolsVersion)
//
//            defaultConfig {
//                minSdkVersion(Build.MinSdk)
//                targetSdkVersion(Build.TargetSdk)
//                versionCode = Build.VersionCode
//                versionName = "0.0.1"
//            }
//        }
//    }
}

tasks.withType<Detekt> {
    parallel = true
    config.setFrom(rootProject.file("detekt-config.yml"))
    setSource(files(projectDir))
    exclude(subprojects.map { "${it.buildDir.relativeTo(rootDir).path}/" })
    reports {
        xml {
            enabled = true
            destination = file("build/reports/detekt/detekt-results.xml")
        }
        html.enabled = false
        txt.enabled = false
    }
}

tasks.register("check") {
    group = "Verification"
    description = "Allows to attach Detekt to the root project."
}

dependencies {
    detekt(DetektDependencies.Formatting)
    detekt(DetektDependencies.Cli)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

fun isNonStable(version: String): Boolean {
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return !regex.matches(version)
}
