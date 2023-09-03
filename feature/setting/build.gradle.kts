plugins {
    kotlin("android")
    id("com.android.library")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.lighthouse.setting"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":common-ui"))

    implementation(libs.bundles.androidx.ui.foundation)
    implementation(libs.bundles.android.basic.ui)
    implementation(libs.kotlin.coroutines)
    implementation(libs.bundles.basic.test)
    implementation(libs.bundles.navigation)
}