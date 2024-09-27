import com.vanniktech.maven.publish.SonatypeHost.Companion.S01
import org.gradle.api.JavaVersion.VERSION_17

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.publish)
}

group = "io.github.jimlyas"
version = "0.1.0"

android {
    namespace = "io.github.jimlyas.reflection.navigation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = VERSION_17
        targetCompatibility = VERSION_17
    }

    kotlinOptions.jvmTarget = "17"
    buildFeatures.compose = true
    publishing.multipleVariants("full") { allVariants() }
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
}

mavenPublishing {
    publishToMavenCentral(S01)
    coordinates(project.group.toString(), project.name, project.version.toString())
    pom {
        name = rootProject.name
        description = "Jetpack Compose Navigation Library using Reflection and GSON"
        inceptionYear = "2024"
        url = "https://github.com/jimlyas/reflection-navigation"

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        developers {
            developer {
                id = "jimlyas"
                name = "Jimly Asshiddiqy"
                url = "https://github.com/jimlyas"
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    // UI
    implementation(libs.compose.navigation)
    implementation(libs.gson)
    implementation(kotlin("reflect"))

    // Testing
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.bundles.uiTooling)
    testImplementation(libs.junit)
}