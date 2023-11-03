plugins {
    kotlin("android")
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.lighthouse.multi_module_navigation"

    defaultConfig {
        applicationId = "com.lighthouse.multi_module_navigation"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true // APK or AAB
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
    implementation(project(":data"))
    implementation(project(":common-ui"))
    implementation(project(":navigation"))
    implementation(project(":feature:home"))
    implementation(project(":feature:setting"))

    implementation(libs.google.admob)

    implementation(libs.bundles.androidx.ui.foundation)
    implementation(libs.bundles.android.basic.ui)
    implementation(libs.bundles.basic.test)
    implementation(libs.bundles.navigation)
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.gson)

    implementation("com.google.firebase:firebase-config-ktx:21.5.0")
    implementation("com.google.firebase:firebase-analytics-ktx:21.5.0")
    implementation(libs.splash.screen)
}