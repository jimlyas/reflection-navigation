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
    coordinates(project.group.toString(), project.name, project.version.toString())
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