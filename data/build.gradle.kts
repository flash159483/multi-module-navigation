plugins {
    kotlin("android")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.lighthouse.data"

    buildTypes {
        debug {
            consumerProguardFile("proguard-rules.pro")
        }
        release {
            consumerProguardFile("proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.hilt)
    kapt(libs.hilt.kapt)
    implementation(libs.bundles.basic.test)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.gson)

    implementation("com.google.firebase:firebase-config-ktx:21.5.0")
    implementation("com.google.firebase:firebase-analytics-ktx:21.5.0")
}