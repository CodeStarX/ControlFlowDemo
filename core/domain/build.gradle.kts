plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "mohsen.soltanian.controlflow.demo.core.domain"
    compileSdk = 33

    defaultConfig {
        minSdk = 23

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
dependencies {
    api(project(":core:data"))

    val coroutine= "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine")

    val timber= "5.0.1"
    implementation("com.jakewharton.timber:timber:$timber")

    val daggerHilt= "2.50"
    implementation("com.google.dagger:hilt-android:$daggerHilt")
    kapt("com.google.dagger:hilt-android-compiler:$daggerHilt")
}