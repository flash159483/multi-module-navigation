plugins {
    kotlin("android")
    id("com.android.application")
    kotlin("kapt")
}

android {
    namespace = "com.lighthouse.multi_module_navigation"

    defaultConfig {
        applicationId = "com.lighthouse.multi_module_navigation"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()
    }

    buildTypes {
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
    implementation(project(":navigation"))
    implementation(project(":feature:home"))
    implementation(project(":feature:setting"))

    implementation(libs.bundles.androidx.ui.foundation)
    implementation(libs.bundles.android.basic.ui)
    implementation(libs.bundles.basic.test)
    implementation(libs.bundles.navigation)
}