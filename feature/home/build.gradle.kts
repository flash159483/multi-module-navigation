plugins {
    kotlin("android")
    id("com.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.lighthouse.home"

    buildTypes {
        debug {
            consumerProguardFile("proguard-rules.pro")
        }
        release {
            consumerProguardFile("proguard-rules.pro")
        }
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":common-ui"))

    implementation(libs.google.admob)
    implementation(libs.bundles.androidx.ui.foundation)
    implementation(libs.bundles.android.basic.ui)
    implementation(libs.kotlin.coroutines)
    implementation(libs.bundles.basic.test)
    implementation(libs.bundles.navigation)
    implementation(libs.swipe.refresh)
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)
    implementation(libs.gson)
}