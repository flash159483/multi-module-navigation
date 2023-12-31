plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.lighthouse.common_ui"

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
    api(project(":domain"))

    implementation(libs.google.admob)
    implementation(libs.bundles.androidx.ui.foundation)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.bundles.basic.test)
    implementation(libs.bundles.navigation)
    implementation("androidx.appcompat:appcompat:1.6.1")
}